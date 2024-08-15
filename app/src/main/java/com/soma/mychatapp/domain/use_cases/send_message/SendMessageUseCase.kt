package com.soma.mychatapp.domain.use_cases.send_message

import com.soma.mychatapp.data.remote.requests.MessageDto
import com.soma.mychatapp.data.remote.responces.MessageResponce
import com.soma.mychatapp.data.repository.Repository

class SendMessageUseCase(
    private val repository: Repository
) {

    operator suspend fun invoke(messageDto: MessageDto):MessageResponce{
        return repository.sendMessage(messageDto)
    }
}