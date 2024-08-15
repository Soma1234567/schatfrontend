package com.soma.mychatapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.soma.mychatapp.presentation.chat_screen.ChatScreen
import com.soma.mychatapp.presentation.home_screen.HomeScreen
import com.soma.mychatapp.presentation.login_screen.LoginScreen
import com.soma.mychatapp.presentation.profile_screen.ProfileScreen
import com.soma.mychatapp.presentation.serachuser_screen.SearchScreen
import com.soma.mychatapp.presentation.signup_screen.SignupScreen
import com.soma.mychatapp.presentation.splash_screen.SplashScreen


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NavGraph(navController:NavHostController,start:String=Screen.SplashScreen.route){
    NavHost(navController = navController, startDestination = start){
        composable(Screen.SplashScreen.route){
            SplashScreen(navController)
        }
        composable(Screen.HomeScreen.route){
            HomeScreen(navController)
        }
        composable(
            Screen.SearchScreen.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(400)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(400)
                )
            }
        ){
            SearchScreen(navController)
        }
        composable(
            route= Screen.ChatScreen.route,
            arguments = listOf(navArgument("username"){type = NavType.StringType}, navArgument("avatar"){type = NavType.IntType}),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(700)
                )
            },

        ){backstack->
            val username = backstack.arguments?.getString("username")
            val avatar = backstack.arguments?.getInt("avatar")
            ChatScreen(navController,username!!,avatar!!)
        }
        composable(route=Screen.SignUpScreen.route){
            SignupScreen(navController)
        }
        composable(route=Screen.LoginScreen.route){
            LoginScreen(navController)
        }
        composable(
            route = Screen.ProfileScreen.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(700)
                )
            }
        ){
            ProfileScreen(navController)
        }
    }
}

fun slideIntoContainer(

){

}