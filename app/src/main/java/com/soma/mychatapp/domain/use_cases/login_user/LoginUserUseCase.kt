package com.soma.mychatapp.domain.use_cases.login_user

import com.soma.mychatapp.data.remote.requests.LoginUser
import com.soma.mychatapp.data.remote.responces.LoginUserResponce
import com.soma.mychatapp.data.repository.Repository

class LoginUserUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(user:LoginUser):LoginUserResponce{
        return repository.loginUser(user)
    }
}