package com.soma.mychatapp.presentation.home_screen

import com.soma.mychatapp.data.local.SingleMessage

data class Chats(
    var alreadyUsers:List<SingleMessage> = emptyList<SingleMessage>(),
)