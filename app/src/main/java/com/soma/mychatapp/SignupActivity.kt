package com.soma.mychatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.otpless.dto.HeadlessRequest
import com.otpless.dto.HeadlessResponse
import com.otpless.main.OtplessManager
import com.otpless.main.OtplessView
import com.soma.schat.R
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SignupActivity : ComponentActivity() {
    var otplessView: OtplessView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize OtplessView
        otplessView = OtplessManager.getInstance().getOtplessView(this);
        otplessView?.initHeadless("OK2MJUHIVHC9XPTLPL9O");
        otplessView?.setHeadlessCallback{response->
            if (response.getStatusCode() == 200) {
                when (response.getResponseType()) {
                    "INITIATE" -> {

                        Toast.makeText(applicationContext,"Initiated", Toast.LENGTH_SHORT).show()
                    }
                    "VERIFY" -> {

                    }
                    "OTP_AUTO_READ" ->{
                        var successResponse: String? = response.getResponse()?.optString("otp")
                    }
                    "ONETAP"->{
                        var responcewithToken: JSONObject = response.response!!
                    }                    }

            } else {
                // handle error
                val error: String? = response.getResponse()?.optString("errorMessage")
                Toast.makeText(applicationContext,error, Toast.LENGTH_SHORT).show()

            }
        }
        setContent {
            val viewModel:MySignUpViewModel = getViewModel()
            var initialize  = remember {
                mutableStateOf(false)
            }
            var phoneNumber = remember {
                mutableStateOf("9182380873")
            }
            var loadingstate = viewModel.loadingstate
            if(loadingstate.value){
                Dialog(onDismissRequest = {}) {
                    Box(modifier = Modifier.size(50.dp), contentAlignment = Alignment.Center){
                        CircularProgressIndicator(
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
            if(!initialize.value){
                SignupScreen(phoneNumber.value) {
                    val request = HeadlessRequest()
                    request.setPhoneNumber("91", phoneNumber.value)
                    otplessView?.startHeadless(request) {response->
                        if (response.getStatusCode() == 200) {
                            when (response.getResponseType()) {
                                "INITIATE" -> {
                                    initialize.value = true
                                    Toast.makeText(applicationContext,"Initiated", Toast.LENGTH_SHORT).show()
                                }
                                "VERIFY" -> {
                                    viewModel.createUser(applicationContext,phoneNumber.value){
                                        finish()
                                        startActivity(Intent(this,MainActivity::class.java))
                                    }

                                }
                                "OTP_AUTO_READ" ->{
                                    var successResponse: String? = response.getResponse()?.optString("otp")
                                    Toast.makeText(applicationContext,successResponse, Toast.LENGTH_SHORT).show()
                                }
                                "ONETAP"->{
                                    var responcewithToken: JSONObject = response.response!!
                                }                    }

                        } else {
                            // handle error
                            val error: String? = response.getResponse()?.optString("errorMessage")
                            Toast.makeText(applicationContext,error, Toast.LENGTH_SHORT).show()

                        }
                    }

                }
            }

            else{
                OtpVerificationScreen(phoneNumber.value,
                    onOtpEntered = {
                        loadingstate.value =true
                        val request = HeadlessRequest()
                        request.setPhoneNumber("91", phoneNumber.value)
                        request.setOtp(it)
                        otplessView?.startHeadless(request){response->
                            if (response.getStatusCode() == 200) {
                                when (response.getResponseType()) {
                                    "INITIATE" -> {
                                        initialize.value = true
                                        Toast.makeText(applicationContext,"Initiated", Toast.LENGTH_SHORT).show()
                                    }
                                    "VERIFY" -> {
                                        viewModel.createUser(applicationContext,phoneNumber.value){
                                            finish()
                                            startActivity(Intent(this,MainActivity::class.java))
                                        }
                                    }
                                    "OTP_AUTO_READ" ->{
                                        var successResponse: String? = response.getResponse()?.optString("otp")
                                        Toast.makeText(applicationContext,successResponse, Toast.LENGTH_SHORT).show()
                                    }
                                    "ONETAP"->{
                                        var responcewithToken: JSONObject = response.response!!
                                    }                    }

                            } else {
                                // handle error
                                val error: String? = response.getResponse()?.optString("errorMessage")
                                Toast.makeText(applicationContext,error, Toast.LENGTH_SHORT).show()

                            }
                        }

                    },
                    onEditClick = {
                        initialize.value = false
                    },
                    onBackClick = {
                        initialize.value = false
                    })
            }

        }
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        otplessView?.onNewIntent(intent)
    }
    override fun onBackPressed() {
        // make sure you call this code before super.onBackPressed();
        if (otplessView?.onBackPressed()!!) return;
        super.onBackPressed()
    }

    fun initHeadless(response: HeadlessResponse){


    }
}


@Composable
fun SignupScreen(phoneNumber: String,onVerify:()->Unit){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF5F5F5)), // Light gray background
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Let's get started",
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Verify your mobile number",
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "by entering it below",
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .height(50.dp)
                .background(color = Color.White, shape = RoundedCornerShape(15.dp))
                .padding(1.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f)
                    .drawBehind {
                        drawLine(
                            color = Color.Black,
                            strokeWidth = 5f,
                            start = Offset(size.width, 0f),
                            end = Offset(size.width, size.height)
                        )
                    },
                contentAlignment = Alignment.Center
            ){
                Text(text = "+91", fontSize = 17.sp)
            }
            OutlinedTextField(
                value = phoneNumber,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 17.sp
                ),
                onValueChange = {},
                singleLine = true,
                modifier = Modifier.weight(8f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Unspecified,
                    unfocusedBorderColor = Color.Unspecified

                )
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "We'll send you a Whatsapp message with a",
            color = Color.Gray,
            fontSize = 15.sp,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "6-digit verification code.",
            color = Color.Gray,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .height(50.dp)
                .background(color = Color(0xFF6CB4EE), shape = RoundedCornerShape(15.dp))
                .clickable {
                    onVerify()
                }
        ){
            Text(text = "CONTINUE", fontSize = 17.sp, color = Color.White, modifier = Modifier.align(
                Alignment.Center))
//                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, tint = Color.White, modifier = Modifier
//                    .align(
//                        Alignment.CenterEnd
//                    )
//                    .width(30.dp))
        }

    }
}

@Composable
fun OtpVerificationScreen(
    phoneNumber:String,
    onOtpEntered: (String) -> Unit,
    onEditClick:()->Unit,
    onBackClick:()->Unit
){
    var otp = remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier
            .align(
                Alignment.TopStart
            )
            .padding(15.dp)
            .clickable {
                onBackClick()
            })
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Verification Code",
                textAlign = TextAlign.Center,
                color = Color.DarkGray,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 20.dp, end = 15.dp),
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "We have sent the verification code to",
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "your mobile number",
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = phoneNumber,
                    textAlign = TextAlign.Center,
                    color = Color.DarkGray,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 20.dp, end = 15.dp),
                )
                Icon(imageVector = Icons.Default.Edit, contentDescription = null, modifier = Modifier.clickable {
                    onEditClick()
                })
            }
            Spacer(modifier = Modifier.height(20.dp))

            BasicTextField(
                value = otp.value,
                onValueChange = {
                    if(it.length<=6){
                        otp.value = it
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                decorationBox = {
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(6){index->
                            val isfocuesd = otp.value.length==index
                            val char = when{
                                index>=otp.value.length->""
                                else->otp.value[index].toString()
                            }
                            Text(
                                text =char,
                                modifier = Modifier
                                    .width(40.dp)
                                    .border(
                                        if (isfocuesd) 2.dp else 1.dp,
                                        if (isfocuesd) Color.DarkGray else Color.LightGray,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(2.dp),
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.DarkGray,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(40.dp))
            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(color = Color(0xFF6CB4EE), shape = RoundedCornerShape(15.dp))
                    .clickable {
                        onOtpEntered(otp.value)
                    },
                contentAlignment = Alignment.Center
            ){
                Text(text = "VERIFY", fontSize = 19.sp, color = Color.White)
            }
        }
    }

}