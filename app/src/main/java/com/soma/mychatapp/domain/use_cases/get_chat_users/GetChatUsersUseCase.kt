package com.soma.mychatapp.domain.use_cases.get_chat_users

import com.soma.mychatapp.data.local.AlreadyUsers
import com.soma.mychatapp.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetChatUsersUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<List<AlreadyUsers>> {
        return repository.getAllChatUsers()
    }
}