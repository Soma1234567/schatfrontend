package com.soma.mychatapp.domain.use_cases.block_user

import com.soma.mychatapp.data.local.BlockUser
import com.soma.mychatapp.data.repository.Repository

class BlockUserUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(user:BlockUser){
        repository.addUsertoBlockList(user)
    }
}