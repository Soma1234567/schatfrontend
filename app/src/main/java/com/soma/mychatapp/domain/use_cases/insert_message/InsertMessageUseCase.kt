package com.soma.mychatapp.domain.use_cases.insert_message

import com.soma.mychatapp.data.local.SingleMessage
import com.soma.mychatapp.data.repository.Repository

class InsertMessageUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(message: SingleMessage){
        repository.insertMessage(message)
    }
}