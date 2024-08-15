package com.soma.mychatapp.presentation.signup_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.soma.mychatapp.navigation.Screen
import com.soma.schat.R
import org.koin.androidx.compose.getViewModel

@Composable
fun SignupScreen(navController: NavHostController,viewModel:SignUpViewModel = getViewModel()) {
    val state by viewModel.state.collectAsState()
    var username = state.username
//    var password = state.password
//    var confirmPassword = state.confirmPassword
//    val showPassword = remember {
//        mutableStateOf(true)
//    }
//    val showConfirmPassword = remember {
//        mutableStateOf(false)
//    }
    if(state.loadingstate){
        Dialog(onDismissRequest = {}) {
            Box(modifier = Modifier.size(50.dp), contentAlignment = Alignment.Center){
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
    val isusernameerror  = state.isUsernameerror
    //val ispassworderror = state.isPasswordError
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .background(color = Color(0xFFF8F8FF)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.chat), contentDescription = null, modifier = Modifier
                .size(70.dp))
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "SChat-Chat with your friends", fontFamily = FontFamily(
                Font(R.font.poetsen)
            ))
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 30.dp)
            ) {
                Text(text = "Create an account", fontSize = 26.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily(
                    Font(
                        R.font.robotobold
                    )
                ))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "your friends are waiting for you! \uD83D\uDE0D", fontSize = 18.sp, color = Color.Gray)
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Username", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = username, onValueChange = viewModel::changeUsername,
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .fillMaxWidth()
                    .imePadding(),
                placeholder = {
                    Text(text = "Username")
                },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black
                ),
                singleLine = true,
                maxLines = 1,
                isError = isusernameerror
            )
            if(isusernameerror){
                Spacer(modifier = Modifier.height(3.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End

                ) {
                    Text(text = "username already taken!", color = MaterialTheme.colorScheme.error)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
//            Text(text = "Password", fontSize = 18.sp)
//            Spacer(modifier = Modifier.height(8.dp))
//            OutlinedTextField(value = password, onValueChange = {
//                viewModel.changePassword(it)
//            },
//                modifier = Modifier
//                    .padding(horizontal = 2.dp)
//                    .fillMaxWidth()
//                    .imePadding(),
//                placeholder = {
//                    Text(text = "Password")
//                },
//                shape = RoundedCornerShape(8.dp),
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = Color.White,
//                    unfocusedContainerColor = Color.White
//                ),
//                textStyle = TextStyle(
//                    fontSize = 20.sp,
//                    color = Color.Black
//                ),
//                visualTransformation = if(showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
//                trailingIcon = {
//                    if(showPassword.value){
//                        Icon(painter = painterResource(id = R.drawable.hide), contentDescription = null, modifier = Modifier
//                            .size(30.dp)
//                            .clickable {
//                                showPassword.value = false
//                            })
//                    }
//                    else{
//                        Icon(painter = painterResource(id = R.drawable.show), contentDescription = null, modifier = Modifier
//                            .size(30.dp)
//                            .clickable {
//                                showPassword.value = true
//                            })
//                    }
//
//                },
//                singleLine = true,
//                maxLines = 1
//            )
//            Spacer(modifier = Modifier.height(12.dp))
//            Text(text = "Confirm Password", fontSize = 18.sp)
//            Spacer(modifier = Modifier.height(8.dp))
//            OutlinedTextField(value = confirmPassword, onValueChange = {
//                viewModel.changeConfirmPassword(it)
//            },
//                modifier = Modifier
//                    .padding(horizontal = 2.dp)
//                    .fillMaxWidth(),
//                placeholder = {
//                    Text(text = "Confirm Password")
//                },
//                shape = RoundedCornerShape(8.dp),
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = Color.White,
//                    unfocusedContainerColor = Color.White
//                ),
//                textStyle = TextStyle(
//                    fontSize = 20.sp,
//                    color = Color.Black
//                ),
//                visualTransformation = if(showConfirmPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
//                trailingIcon = {
//                    if(showConfirmPassword.value){
//                        Icon(painter = painterResource(id = R.drawable.hide), contentDescription = null, modifier = Modifier
//                            .size(30.dp)
//                            .clickable {
//                                showConfirmPassword.value = false
//                            })
//                    }
//                    else{
//                        Icon(painter = painterResource(id = R.drawable.show), contentDescription = null, modifier = Modifier
//                            .size(30.dp)
//                            .clickable {
//                                showConfirmPassword.value = true
//                            })
//                    }
//                }
//                , singleLine = true,
//                maxLines = 1,
//                isError = ispassworderror
//            )
//            if(ispassworderror){
//                Spacer(modifier = Modifier.height(3.dp))
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.End
//
//                ) {
//                    Text(text = "passwords do not match!", color = MaterialTheme.colorScheme.error)
//                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.BottomCenter)
                    .background(color = Color(0xFF89CFF0), shape = RoundedCornerShape(12.dp))
                    .clickable {
                        viewModel.valiateresult(context) {
                            navController.popBackStack()
                            navController.navigate(Screen.HomeScreen.route)
                        }
                    },
                contentAlignment = Alignment.Center
            ){
                Text(text = "Sign Up", fontSize = 20.sp, color = Color.White)
            }


        }
//        Spacer(modifier = Modifier.height(20.dp))
//        Row(
//            modifier = Modifier
//                .padding(bottom = 20.dp)
//                .fillMaxWidth()
//            ,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Text(text = "Have an account?", fontSize = 18.sp, color = Color.DarkGray)
//            Spacer(modifier = Modifier.width(4.dp))
//            Text(text = "Login", fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Bold, modifier = Modifier.clickable {
//                navController.navigate(Screen.LoginScreen.route)
//            })
//        }
}
