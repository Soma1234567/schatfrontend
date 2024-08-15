package com.soma.mychatapp.domain.use_cases.save_username

import com.soma.mychatapp.data.repository.Repository

class SaveUsernameUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(username:String){
        repository.saveUsername(username)
    }
}