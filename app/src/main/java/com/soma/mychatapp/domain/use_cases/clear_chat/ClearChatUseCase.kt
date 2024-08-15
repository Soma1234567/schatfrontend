package com.soma.mychatapp.domain.use_cases.clear_chat

import com.soma.mychatapp.data.repository.Repository

class ClearChatUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(me:String, username:String){
        repository.clearChat(me, username)
    }
}