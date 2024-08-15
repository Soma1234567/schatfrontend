package com.soma.mychatapp.presentation.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.soma.mychatapp.navigation.Screen
import com.soma.schat.R
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel

@Composable
fun SplashScreen(navController: NavHostController,viewModel:SplashViewModel = getViewModel()){
    val username by viewModel.username.collectAsState()
    LaunchedEffect(Unit){
        delay(2000)
        if(username.isEmpty()){
            navController.popBackStack()
            navController.navigate(Screen.SignUpScreen.route)
        }
        else{
            navController.popBackStack()
            navController.navigate(Screen.HomeScreen.route)
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White)
    ){
        Image(painter = painterResource(id = R.drawable.splashchat), contentDescription = null, modifier = Modifier
            .align(
                Alignment.Center
            )
            .size(150.dp))
        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Schat", fontSize = 19.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "from soma", fontSize = 16.sp, color = Color.Gray)
        }
    }
}