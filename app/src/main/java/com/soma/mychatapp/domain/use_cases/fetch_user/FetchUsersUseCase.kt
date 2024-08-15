package com.soma.mychatapp.domain.use_cases.fetch_user

import com.soma.mychatapp.data.remote.requests.SearchUser
import com.soma.mychatapp.data.repository.Repository

class FetchUsersUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(query:String):List<SearchUser>{
        return repository.fetchUsers(query)
    }
}