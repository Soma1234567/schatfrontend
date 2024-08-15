package com.soma.mychatapp.data.repository

import com.soma.mychatapp.data.local.AlreadyUsers
import com.soma.mychatapp.data.local.BlockUser
import com.soma.mychatapp.data.local.MessageDatabase
import com.soma.mychatapp.data.local.SingleMessage
import com.soma.mychatapp.data.remote.ChatApi
import com.soma.mychatapp.data.remote.requests.CreateUser
import com.soma.mychatapp.data.remote.requests.DeleteMessage
import com.soma.mychatapp.data.remote.requests.LoginUser
import com.soma.mychatapp.data.remote.requests.MessageDto
import com.soma.mychatapp.data.remote.requests.ReadMessage
import com.soma.mychatapp.data.remote.requests.SearchUser
import com.soma.mychatapp.data.remote.requests.UpdateAvatar
import com.soma.mychatapp.data.remote.responces.CreateUserResponce
import com.soma.mychatapp.data.remote.responces.LoginUserResponce
import com.soma.mychatapp.data.remote.responces.MessageResponce
import com.soma.mychatapp.data.remote.responces.UpdateAvatarResponce
import com.soma.mychatapp.domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow

class Repository(
    private val dataStoreOperations: DataStoreOperations,
    private val chatApi: ChatApi,
    private val messageDatabase: MessageDatabase
) {
    suspend fun saveUsername(username:String){
        dataStoreOperations.saveUsername(username)
    }
    fun readUsername():Flow<String>{
        return dataStoreOperations.readUsername()
    }

    suspend fun createUser(user: CreateUser):CreateUserResponce{
        return chatApi.createUser(user)
    }

    suspend fun loginUser(user:LoginUser):LoginUserResponce{
        return chatApi.loginUser(user)
    }

    suspend fun fetchUsers(query:String):List<SearchUser>{
        return chatApi.fetchUsers(query)
    }

    suspend fun sendMessage(messageDto: MessageDto):MessageResponce{
        return chatApi.sendMessage(messageDto)
    }

    suspend fun updateToken(username:String,token:String){
        chatApi.updateToken(username,token)
    }

    suspend fun insertMessage(singleMessage: SingleMessage){
        return messageDatabase.getDao().insertMessage(singleMessage)
    }

    fun readMessages(user:String):Flow<List<SingleMessage>>{
        return messageDatabase.getDao().getChatByUser(user)
    }

    suspend fun addUsertoBlockList(user:BlockUser){
        messageDatabase.getDao().blockuser(user)
    }

    fun getBlockList():Flow<List<BlockUser>>{
        return messageDatabase.getDao().getblocklist()
    }

    suspend fun unblockUser(username:String){
        messageDatabase.getDao().unblockUser(username)
    }

    suspend fun updateToReadMessage(readMessage: ReadMessage){
        chatApi.updateToReadMessages(readMessage)
    }

    suspend fun updateMessageToSent(status:String,millis:String){
        messageDatabase.getDao().updatemessageToSent(status,millis)
    }

    suspend fun deleteForMeMessage(millis: String){
        messageDatabase.getDao().deleteForMeMessage(millis)
    }

    suspend fun deleteMessageForEveryone(deleteMessage: DeleteMessage){
        chatApi.deleteMessageForEveryOne(deleteMessage)
    }
    suspend fun updateDeletedMessage(message:String,millis: String,deletedstatus:String){
        messageDatabase.getDao().updateDeletedMessage(message,millis,deletedstatus)
    }

    suspend fun unsendMessage(unsendMessage:DeleteMessage){
        chatApi.unsendMessage(unsendMessage)
    }

    suspend fun getMessageStatus(millis:String):String{
        return messageDatabase.getDao().getMessageStatus(millis)
    }

    suspend fun clearChat(me:String,username: String){
        messageDatabase.getDao().clearChat(me, username)
    }

     fun getAlreadyChatUsers2():Flow<List<SingleMessage>>{
        return messageDatabase.getDao().getAlreadyChatUsers()
    }



    suspend fun deleteALreadyChatUser(username: String){
        messageDatabase.getDao().deleteAlreadyChatUser(username)
    }

    fun getAllChatUsers():Flow<List<AlreadyUsers>>{
        return messageDatabase.getDao().getAllChatUsers()
    }

    suspend fun updateAvatar(updateAvatar: UpdateAvatar):UpdateAvatarResponce{
        return chatApi.updateAvatar(updateAvatar)
    }

    suspend fun saveAvatar(avatar:Int){
        dataStoreOperations.saveAvatar(avatar)
    }

    fun readAvatar():Flow<Int>{
        return dataStoreOperations.readAvatar()
    }

    suspend fun connect(){
        chatApi.connect()
    }
}