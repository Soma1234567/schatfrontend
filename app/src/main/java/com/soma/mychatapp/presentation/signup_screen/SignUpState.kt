package com.soma.mychatapp.presentation.signup_screen

data class SignUpState(
    val username:String="",
    val password:String="",
    val confirmPassword:String="",
    val loadingstate:Boolean = false,
    val isUsernameerror:Boolean = false,
    val isPasswordError:Boolean = false
)