package com.soma.mychatapp.domain.use_cases.connect

import com.soma.mychatapp.data.remote.responces.CreateUserResponce
import com.soma.mychatapp.data.repository.Repository

class ConnectUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(){
        repository.connect()
    }
}