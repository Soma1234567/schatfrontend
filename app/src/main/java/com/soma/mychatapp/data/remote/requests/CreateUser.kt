package com.soma.mychatapp.data.remote.requests
data class CreateUser(
    val username:String,
    val password:String,
    val token:String,
    val avatar:Int
)
