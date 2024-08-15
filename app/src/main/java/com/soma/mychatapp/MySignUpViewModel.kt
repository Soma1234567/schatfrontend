package com.soma.mychatapp

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.soma.mychatapp.data.remote.requests.CreateUser
import com.soma.mychatapp.data.repository.Repository
import com.soma.mychatapp.domain.use_cases.Usecases
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MySignUpViewModel(
    private val usecases: Usecases
):ViewModel() {
    var saved = mutableStateOf(false)
    val loadingstate = mutableStateOf(false)
    fun savePhoneNumber(phoneNumber:String){
        viewModelScope.launch{
            usecases.saveUsernameUseCase(phoneNumber)
            saved.value = true
        }
    }
    fun createUser(context: Context, phoneNumber: String, success:()->Unit){
        viewModelScope.launch {
            try{
                val token = Firebase.messaging.token.await()
                val result = usecases.createUserUsecase(CreateUser(phoneNumber,"",token,0))
                savePhoneNumber(phoneNumber)
                success()

            }
            catch (e: IOException){
                Toast.makeText(context,"Io exception",Toast.LENGTH_SHORT).show()
                loadingstate.value = false
            }
            catch (e: UnknownHostException){
                Toast.makeText(context,"unknown host exception",Toast.LENGTH_SHORT).show()

                loadingstate.value = false
            }
            catch (e: ConnectException){
                Toast.makeText(context,"connection exception",Toast.LENGTH_SHORT).show()

                loadingstate.value = false
            }
            catch (e: SocketTimeoutException){
                Toast.makeText(context,"timeout exception",Toast.LENGTH_SHORT).show()
                loadingstate.value = false
            }

        }
    }
}