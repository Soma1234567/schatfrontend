package com.soma.mychatapp.domain.use_cases.delete_alraedy_user

import com.soma.mychatapp.data.repository.Repository

class DeleteALreadyChatUserUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(username:String){
        repository.deleteALreadyChatUser(username)
    }
}