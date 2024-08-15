package com.soma.mychatapp

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.soma.mychatapp.data.remote.ChatApi
import com.soma.mychatapp.navigation.NavGraph
import com.soma.mychatapp.ui.theme.SChatTheme
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val chatapi = get<ChatApi>()
        setContent {
            SChatTheme {
                LaunchedEffect(key1 = Unit){
                    try {
                        chatapi.connect()
                    }
                    catch (e:Exception){

                    }
                }
                val navController = rememberNavController()
                NavGraph(navController = navController)
//                SignupScreen(navController = navController)
            }
        }
    }
}

