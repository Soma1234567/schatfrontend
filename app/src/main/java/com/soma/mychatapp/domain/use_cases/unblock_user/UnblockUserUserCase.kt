package com.soma.mychatapp.domain.use_cases.unblock_user

import com.soma.mychatapp.data.repository.Repository

class UnblockUserUserCase(
    private val repository: Repository
) {
    suspend operator fun invoke(username:String){
        repository.unblockUser(username)
    }
}