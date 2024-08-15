package com.soma.mychatapp.domain.use_cases.read_username

import com.soma.mychatapp.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadUsernameUsecase(
    private val repository: Repository
) {
    operator fun invoke():Flow<String>{
        return repository.readUsername()
    }
}