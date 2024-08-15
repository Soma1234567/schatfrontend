package com.soma.mychatapp.presentation.serachuser_screen

import com.soma.mychatapp.data.remote.requests.SearchUser

data class SearchState(
    val users:List<SearchUser> = listOf(),
    val loadingState:Boolean = false,
    val size:Int = -1
)
