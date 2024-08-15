package com.soma.mychatapp.navigation

sealed class Screen(val route:String) {
    object SplashScreen:Screen("splash_screen")
    object HomeScreen:Screen(route = "home_screen")
    object SearchScreen:Screen(route = "search_screen")
    object ChatScreen:Screen(route = "chat_screen/{username}/{avatar}"){
        fun passusername(username:String,number:String):String{
            return "chat_screen/$username/$number"
        }
    }
    object SignUpScreen:Screen(route = "signup_screen")
    object LoginScreen:Screen(route = "login_screen")
    object ProfileScreen:Screen(route ="profile_screen")
}