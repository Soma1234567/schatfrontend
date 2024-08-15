package com.soma.mychatapp.domain.use_cases.create_user

import com.soma.mychatapp.data.remote.requests.CreateUser
import com.soma.mychatapp.data.remote.responces.CreateUserResponce
import com.soma.mychatapp.data.repository.Repository

class CreateUserUsecase(
    private val repository: Repository
) {
    suspend operator fun invoke(user: CreateUser):CreateUserResponce{
        return repository.createUser(user)
    }
}