package com.soma.mychatapp

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.soma.mychatapp.data.remote.requests.ReadMessage
import com.soma.mychatapp.domain.use_cases.Usecases
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ReadMessageWorker(
    private val context: Context,
    private val parameters: WorkerParameters
):CoroutineWorker(context,parameters),KoinComponent {
    override suspend fun doWork(): Result {
        val usecases:Usecases by inject()
        val whom = parameters.inputData.getString("whom")!!
        val who = parameters.inputData.getString("who")!!
        try{
            usecases.updateReadMessagesUseCase(ReadMessage(who,whom))
        }
        catch(e:Exception){

        }
        return Result.success()
    }
}