package com.soma.mychatapp.presentation.chat_screen

import com.soma.mychatapp.data.local.SingleMessage

data class ChatState(
    val message:String = "",
    val messages:List<SingleMessage> = emptyList(),
    val username:String = "",
    val avatar:Int = 0,
)
