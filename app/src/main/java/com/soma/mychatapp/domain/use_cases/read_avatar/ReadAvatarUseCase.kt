package com.soma.mychatapp.domain.use_cases.read_avatar

import com.soma.mychatapp.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadAvatarUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<Int> {
        return repository.readAvatar()
    }
}