package com.soma.mychatapp.domain.use_cases.delete_for_me_message

import com.soma.mychatapp.data.repository.Repository

class DeleteForMeUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(millis: String){
        repository.deleteForMeMessage(millis)
    }
}