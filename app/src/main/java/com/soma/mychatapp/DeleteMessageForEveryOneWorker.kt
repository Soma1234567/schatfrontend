package com.soma.mychatapp

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.soma.mychatapp.data.remote.requests.DeleteMessage
import com.soma.mychatapp.domain.use_cases.Usecases
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DeleteMessageForEveryOneWorker(
    private val context: Context,
    private val params:WorkerParameters
):CoroutineWorker(context,params),KoinComponent {
    override suspend fun doWork(): Result {
        val usecases:Usecases by inject()
        val who = params.inputData.getString("who")!!
        val whom = params.inputData.getString("whom")!!
        val millis = params.inputData.getString("millis")!!
        try{
            usecases.deleteForEveryOneUseCase(DeleteMessage(who,whom,millis))
        }
        catch (e:Exception){

        }
        return Result.success()
    }
}