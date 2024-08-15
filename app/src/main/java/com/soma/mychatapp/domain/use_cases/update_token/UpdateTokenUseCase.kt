package com.soma.mychatapp.domain.use_cases.update_token

import com.soma.mychatapp.data.repository.Repository

class UpdateTokenUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(username:String,token:String){
        repository.updateToken(username,token)
    }

}