package com.soma.mychatapp.domain.use_cases.get_already_chat_users

import com.soma.mychatapp.data.local.SingleMessage
import com.soma.mychatapp.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetAlreadyChatUsersUseCase(
    private val repository: Repository
) {
    operator fun invoke():Flow<List<SingleMessage>>{
        return repository.getAlreadyChatUsers2()
    }
}