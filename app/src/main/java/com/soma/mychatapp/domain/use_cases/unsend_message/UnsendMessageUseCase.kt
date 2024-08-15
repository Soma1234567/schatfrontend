package com.soma.mychatapp.domain.use_cases.unsend_message

import com.soma.mychatapp.data.remote.requests.DeleteMessage
import com.soma.mychatapp.data.repository.Repository

class UnsendMessageUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(unsendMessage:DeleteMessage){
        repository.unsendMessage(unsendMessage)
    }
}