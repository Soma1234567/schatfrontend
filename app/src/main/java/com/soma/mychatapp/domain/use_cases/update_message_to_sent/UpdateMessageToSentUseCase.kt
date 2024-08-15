package com.soma.mychatapp.domain.use_cases.update_message_to_sent

import com.soma.mychatapp.data.repository.Repository

class UpdateMessageToSentUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(status:String, millis: String){
        repository.updateMessageToSent(status,millis)
    }
}