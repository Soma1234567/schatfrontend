package com.soma.mychatapp.domain.use_cases.update_read_messages

import com.soma.mychatapp.data.remote.requests.ReadMessage
import com.soma.mychatapp.data.repository.Repository

class UpdateReadMessagesUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(readMessage: ReadMessage){
        repository.updateToReadMessage(readMessage)
    }
}