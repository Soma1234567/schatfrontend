package com.soma.mychatapp.domain.use_cases.read_messages

import com.soma.mychatapp.data.local.SingleMessage
import com.soma.mychatapp.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadMessagesUseCase(
    private val repository: Repository
) {
    operator suspend fun invoke(user:String):Flow<List<SingleMessage>>{
        return repository.readMessages(user)
    }
}