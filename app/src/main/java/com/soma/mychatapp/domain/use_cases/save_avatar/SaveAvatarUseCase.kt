package com.soma.mychatapp.domain.use_cases.save_avatar

import com.soma.mychatapp.data.repository.Repository

class SaveAvatarUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(avatar:Int){
        repository.saveAvatar(avatar)
    }
}