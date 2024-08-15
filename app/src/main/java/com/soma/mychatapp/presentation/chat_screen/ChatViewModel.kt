package com.soma.mychatapp.presentation.chat_screen

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.soma.mychatapp.DeleteMessageForEveryOneWorker
import com.soma.mychatapp.ReadMessageWorker
import com.soma.mychatapp.SendMessageWorker
import com.soma.mychatapp.UnsendMessageWorker
import com.soma.mychatapp.data.local.SingleMessage
import com.soma.mychatapp.data.local.BlockUser
import com.soma.mychatapp.domain.use_cases.Usecases
import com.soma.mychatapp.util.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ChatViewModel(
    private val usecases: Usecases
):ViewModel() {
    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()
    private val _messages = MutableStateFlow((emptyList<SingleMessage>()))
     var messages = _messages.asStateFlow()
    private val _blocklist  = MutableStateFlow(emptyList<BlockUser>())
    var blocklist = _blocklist.asStateFlow()
    init {
        viewModelScope.launch {
            blocklist = usecases.readBlockListUseCase().stateIn(viewModelScope)
        }
    }
    fun changeMessage(new:String){
        _state.value = _state.value.copy(
            message = new
        )
    }
    fun setAvatar(avatar:Int){
        _state.value = _state.value.copy(
            avatar = avatar
        )
    }
    fun loadMessages(to:String) {
        viewModelScope.launch {
            messages = usecases.readMessagesUseCase(to).stateIn(viewModelScope)
            _state.value = _state.value.copy(
                username = usecases.readUsernameUsecase().stateIn(viewModelScope).value,
            )
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMesaage(context:Context, to:String){
        if(state.value.message.isEmpty()){
            Toast.makeText(context,"Enter message",Toast.LENGTH_SHORT).show()
        }
        else{
            val currentDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val formattedDate = currentDate.format(formatter)
            val currentTime = LocalTime.now()
            val timeFormatter = DateTimeFormatter.ofPattern("HH:mm a")
            val formattedTime = currentTime.format(timeFormatter)
            val millis = System.currentTimeMillis().toString()
            val request = OneTimeWorkRequestBuilder<SendMessageWorker>()
                .setInputData(
                    workDataOf(
                        "to" to to,
                        "from" to state.value.username,
                        "message" to state.value.message,
                        "millis" to millis,
                        "formatteddate" to formattedDate,
                        "formattedtime" to formattedTime
                    )

                ).setConstraints(
                    Constraints(
                        requiredNetworkType = NetworkType.CONNECTED
                    )
                )

                .build()
            WorkManager.getInstance(context).enqueue(request)
            viewModelScope.launch(Dispatchers.IO) {
                val from = state.value.username
                usecases.insertMessageUseCase(SingleMessage(state.value.username,to,state.value.message,formattedTime,formattedDate,millis,Status.NOT_SENT.name,state.value.avatar.toString(),to))
                usecases.deleteALreadyChatUserUseCase(to)
                _state.value = _state.value.copy(
                    message = ""
                )
            }
        }
    }
    fun setUnreadToZero(to:String){
        viewModelScope.launch(Dispatchers.IO) {
            usecases.deleteALreadyChatUserUseCase(to)
        }
    }
    fun blockuser(to:String){
     viewModelScope.launch {
         if(blocklist.value.contains(BlockUser(to))){
            usecases.unblockUserUserCase(to)
         }
         else{
             usecases.blockUserUseCase(BlockUser(to))
         }

     }

    }
    
    fun readTheirMessages(whom:String,context: Context){
        val request = OneTimeWorkRequestBuilder<ReadMessageWorker>()
            .setInputData(
                workDataOf(
                    "who" to state.value.username,
                    "whom" to whom
                )
            )
            .setConstraints(
                Constraints(
                    requiredNetworkType = NetworkType.CONNECTED
                )
            )
            .build()
        WorkManager.getInstance(context).enqueue(request)

    }

    fun deleteMessageForMe(millis:String){
        viewModelScope.launch(Dispatchers.IO) {
            usecases.deleteForMeUseCase(millis)
        }
    }

    fun deleteMessageForEveryOne(context: Context,whom:String,millis:String){
        viewModelScope.launch(Dispatchers.IO) {
            usecases.updateDeletedMessageUseCase("you deleted this message",millis,Status.DELETED.name)
        }
        val request = OneTimeWorkRequestBuilder<DeleteMessageForEveryOneWorker>()
            .setInputData(
                workDataOf(
                    "who" to state.value.username,
                    "whom" to whom,
                    "millis" to millis
                )
            )
            .setConstraints(
                Constraints(
                    requiredNetworkType = NetworkType.CONNECTED
                )
            )
            .build()
        WorkManager.getInstance(context).enqueue(request)
    }

    fun unsendMessage(context: Context,whom:String,millis:String){
        viewModelScope.launch(Dispatchers.IO) {
            usecases.updateDeletedMessageUseCase("You unsended this message",millis,Status.UNSEND.name)
        }
        val request = OneTimeWorkRequestBuilder<UnsendMessageWorker>()
            .setInputData(
                workDataOf(
                    "who" to state.value.username,
                    "whom" to whom,
                    "millis" to millis
                )
            )
            .setConstraints(
                Constraints(
                    requiredNetworkType = NetworkType.CONNECTED
                )
            )
            .build()
        WorkManager.getInstance(context).enqueue(request)
    }

    fun clearChat(username:String){
        viewModelScope.launch(Dispatchers.IO) {
            usecases.clearChatUseCase(state.value.username,username)
        }
    }

}