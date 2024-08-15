package com.soma.mychatapp.presentation.signup_screen

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.soma.mychatapp.data.remote.requests.CreateUser
import com.soma.mychatapp.domain.use_cases.Usecases
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class SignUpViewModel(
    private val usecases: Usecases
):ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    fun changeUsername(newUsername:String){
        _state.value = _state.value.copy(username = newUsername)
    }

    fun changePassword(newPassword:String){
        _state.value = _state.value.copy(password = newPassword)
    }
    fun changeConfirmPassword(newPassword:String){
        _state.value = _state.value.copy(confirmPassword = newPassword)
    }

    fun valiateresult(context: Context, sucess:()->Unit){
        if(state.value.username.isNotBlank()){
            viewModelScope.launch {
                _state.value = _state.value.copy(loadingstate = true)
                delay(1000)
                try{
                    val token = Firebase.messaging.token.await()
                    val result = usecases.createUserUsecase(CreateUser(state.value.username,state.value.password,token,0))
                    if(!result.iscreated){
                        _state.value = _state.value.copy(isUsernameerror = true, loadingstate = false)
                    }
                    else{
                        usecases.saveUsernameUseCase(state.value.username)
                        sucess()
                    }
                }
                catch (e:IOException){
                    Toast.makeText(context,"Network error",Toast.LENGTH_SHORT).show()
                    _state.value = _state.value.copy(loadingstate = false)
                }
                catch (e:UnknownHostException){
                    Toast.makeText(context,"Please Check your internet connection",Toast.LENGTH_SHORT).show()
                    _state.value = _state.value.copy(loadingstate = false)
                }
                catch (e:ConnectException){
                    Toast.makeText(context,"Please Check your internet connection",Toast.LENGTH_SHORT).show()
                    _state.value = _state.value.copy(loadingstate = false)
                }
                catch (e:SocketTimeoutException){
                    Toast.makeText(context,"Please Check your internet connection",Toast.LENGTH_SHORT).show()
                    _state.value = _state.value.copy(loadingstate = false)
                }


            }
        }
        else{
            Toast.makeText(context,"Please Enter username",Toast.LENGTH_SHORT).show()
        }

    }
}