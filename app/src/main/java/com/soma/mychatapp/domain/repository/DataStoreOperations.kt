package com.soma.mychatapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    suspend fun saveUsername(username:String)
    fun readUsername():Flow<String>

    suspend fun saveAvatar(avatar:Int)
    fun readAvatar():Flow<Int>
}