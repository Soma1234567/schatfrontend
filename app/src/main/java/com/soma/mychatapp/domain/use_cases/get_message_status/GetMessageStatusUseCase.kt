package com.soma.mychatapp.domain.use_cases.get_message_status

import com.soma.mychatapp.data.repository.Repository

class GetMessageStatusUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(millis:String):String{
        return repository.getMessageStatus(millis)
    }
}