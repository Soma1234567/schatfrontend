package com.soma.mychatapp

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.soma.mychatapp.data.local.AlreadyUsers
import com.soma.mychatapp.data.local.BlockUser
import com.soma.mychatapp.data.local.MessageDatabase
import com.soma.mychatapp.data.local.SingleMessage
import com.soma.mychatapp.data.remote.ChatApi
import com.soma.mychatapp.data.remote.requests.MessageRecieved
import com.soma.mychatapp.domain.use_cases.read_username.ReadUsernameUsecase
import com.soma.mychatapp.presentation.home_screen.images
import com.soma.mychatapp.util.Constants
import com.soma.mychatapp.util.Status
import com.soma.schat.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

class MessagingService:FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val database = get<MessageDatabase>().getDao()
        val api = get<ChatApi>()
        val blocklist = database.getblocklistWithoutFlow()
        if(message.data["type"]=="message"){
            if(!blocklist.contains(BlockUser( message.data["fr"]!!))){
                CoroutineScope(Dispatchers.IO).launch {
                    //make api which states user received message
                    val from = message.data["fr"]!!
                    val to = message.data["to"]!!
                    api.messageRecieved(MessageRecieved(message.data["fr"]!!,message.data["millis"]!!))
                    database.insertAlreadyChatUser(AlreadyUsers(from))
                    database.insertMessage(SingleMessage(from,to,message.data["message"]!!,message.data["time"]!!,message.data["date"]!!,message.data["millis"]!!,Status.RECEIVED_THEIR_MESSAGE.name,message.data["avatar"]!!,from))
                }
                val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                val pd = PendingIntent.getActivity(
                    this,
                    1,
                    Intent(this,MainActivity::class.java),
                    PendingIntent.FLAG_IMMUTABLE
                )
                val builder = NotificationCompat.Builder(
                    this,
                    Constants.CHANNEL_ID,
                )
                    .setContentTitle(message.data["fr"])
                    .setContentText(message.data["message"])
                    .setSmallIcon(R.drawable.chat)
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            this.resources,
                            images[message.data["avatar"]?.toInt()!!]
                        )
                    )
                    .addAction(
                        0,
                        "View",
                        pd
                    )
                    .setContentIntent(pd)

                nm.notify(2,builder.build())
            }
        }
        else if(message.data["type"]=="messagerecieved"){
            val millis = message.data["millis"]!!
            CoroutineScope(Dispatchers.IO).launch {
                val status = database.getMessageStatus(millis)
                if(status!=Status.DELETED.name && status!=Status.UNSEND.name){
                    database.updateMessageStatus(Status.RECIEVED_MY_MESSAGE.name,millis)
                }
            }
        }
        else if(message.data["type"]=="messagereaded"){
            val whoreaded = message.data["whoreaded"]!!
            CoroutineScope(Dispatchers.IO).launch {
                database.updatemessagestatustoRead(Status.RECIEVED_MY_MESSAGE.name,whoreaded,Status.READ_MY_MESSAGE.name)
            }

        }
        else if(message.data["type"]=="deletemessage"){
            val who = message.data["who"]!!
            val whom = message.data["whom"]!!
            val millis = message.data["millis"]!!
            CoroutineScope(Dispatchers.IO).launch {
                database.updateDeletedMessage("This message was deleted",millis,Status.DELETED.name)
            }
        }

        else if(message.data["type"]=="unsendmessage"){
            val who = message.data["who"]!!
            val whom = message.data["whom"]!!
            val millis = message.data["millis"]!!
            CoroutineScope(Dispatchers.IO).launch {
                database.deleteForMeMessage(millis)
            }
        }

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val api = get<ChatApi>()
        val usecase = get<ReadUsernameUsecase>()
        CoroutineScope(Dispatchers.IO).launch{
            try{
                val username = usecase().stateIn(this).value
                api.updateToken(username,token)
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}