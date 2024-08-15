package com.soma.mychatapp.presentation.serachuser_screen

import com.soma.mychatapp.data.local.Contact
import com.soma.mychatapp.data.remote.requests.SearchUser
import com.soma.mychatapp.data.repository.ContactRepository

data class SearchState(
    val users:List<Contact> = emptyList(),
    val loadingState:Boolean = false
)
