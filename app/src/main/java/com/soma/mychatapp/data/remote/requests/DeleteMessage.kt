package com.soma.mychatapp.data.remote.requests

data class DeleteMessage(
    val who:String,
    val whom:String,
    val millis:String
)
