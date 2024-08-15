package com.soma.mychatapp.domain.use_cases.delete_for_everyone

import com.soma.mychatapp.data.remote.requests.DeleteMessage
import com.soma.mychatapp.data.repository.Repository

class DeleteForEveryOneUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(deleteMessage: DeleteMessage){
        repository.deleteMessageForEveryone(deleteMessage)
    }
}