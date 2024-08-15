package com.soma.mychatapp

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.soma.mychatapp.data.remote.requests.MessageDto
import com.soma.mychatapp.domain.use_cases.Usecases
import com.soma.mychatapp.util.Status
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SendMessageWorker(
    private val appContext:Context,
    private val parameters: WorkerParameters
):CoroutineWorker(appContext,parameters),KoinComponent {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        val usecases:Usecases by inject()
        val to = parameters.inputData.getString("to")!!
        val username = parameters.inputData.getString("from")!!
        val message = parameters.inputData.getString("message")!!
        val millis = parameters.inputData.getString("millis")!!
        val formattedTime = parameters.inputData.getString("formattedtime")!!
        val formattedDate = parameters.inputData.getString("formatteddate")!!
        try{
            val status = usecases.getMessageStatusUseCase(millis)
            if(status!=Status.DELETED.name && status!=Status.UNSEND.name){
                val messageresponce  = usecases.sendMessageUseCase(MessageDto(username,to,message,formattedTime,formattedDate,millis))
                if(messageresponce.issent) {
                    usecases.updateMessageToSentUseCase(Status.SENT_MY_MESSAGE.name, millis)
                }
            }
        }
        catch (e:Exception){
            return Result.retry()
        }
        return Result.success()
    }
}