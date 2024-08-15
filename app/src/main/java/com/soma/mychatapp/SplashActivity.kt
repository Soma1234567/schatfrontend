package com.soma.mychatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.soma.schat.R
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var viewModel:MySplashViewModel = getViewModel()
            LaunchedEffect(Unit){
                delay(2000L)
                if(viewModel.phoneNumber.value.isEmpty()){
                    finish()
                    startActivity(Intent(applicationContext,SignupActivity::class.java))
                }
                else{
                    finish()
                    startActivity(Intent(applicationContext,MainActivity::class.java))
                }
            }
            Column(
                modifier = Modifier.fillMaxSize()
                    .background(color = Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = R.drawable.splashchat), contentDescription = null, modifier = Modifier.size(200.dp))
            }
        }
    }
}