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
import com.soma.mychatapp.presentation.profile_screen.ProfileScreen
import com.soma.mychatapp.presentation.serachuser_screen.SearchScreen


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NavGraph(navController:NavHostController,start:String = Screen.HomeScreen.route){
    NavHost(navController = navController, startDestination = start){

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
            val number = backstack.arguments?.getString("number")
            ChatScreen(navController,username!!,number!!)
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

