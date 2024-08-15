package com.soma.mychatapp.presentation.login_screen

data class LoginState (
    val username:String = "",
    val password:String="",
    val loadingState:Boolean = false,
    val iserror:Boolean=false
)