package com.soma.mychatapp.domain.use_cases.update_deleted_message

import com.soma.mychatapp.data.repository.Repository

class UpdateDeletedMessageUseCase(
    private val repository: Repository
) {
    operator suspend fun invoke(message:String,millis: String,deletedstatus:String){
        repository.updateDeletedMessage(message,millis,deletedstatus)
    }
}