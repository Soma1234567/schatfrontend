package com.soma.mychatapp.data.remote.requests

data class MessageDto(
    val from:String,
    val to:String,
    val message: String,
    val time:String,
    val date:String,
    val millis:String
)