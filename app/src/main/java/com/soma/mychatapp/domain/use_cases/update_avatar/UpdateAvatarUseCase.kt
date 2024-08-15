package com.soma.mychatapp.domain.use_cases.update_avatar

import com.soma.mychatapp.data.remote.requests.UpdateAvatar
import com.soma.mychatapp.data.remote.responces.UpdateAvatarResponce
import com.soma.mychatapp.data.repository.Repository

class UpdateAvatarUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(updateAvatar: UpdateAvatar):UpdateAvatarResponce{
        return repository.updateAvatar(updateAvatar)
    }
}