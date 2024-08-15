package com.soma.mychatapp.presentation.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soma.mychatapp.data.remote.requests.LoginUser
import com.soma.mychatapp.domain.use_cases.Usecases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val usecases: Usecases
):ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()
    fun changeUsername(new:String){
        _state.value = _state.value.copy(
            username = new
        )
    }

    fun changePassword(new:String){
        _state.value = _state.value.copy(
            password = new
        )
    }

    fun validateResult(onsuccess:()->Unit){
        viewModelScope.launch {
            _state.value = _state.value.copy(loadingState = true)
            val result = usecases.loginUserUseCase(LoginUser(state.value.username,state.value.password))
            if(!result.iscreated){
                _state.value = _state.value.copy(
                    iserror = true,
                    loadingState = false
                )
            }
            else{
                usecases.saveUsernameUseCase(state.value.username)
                _state.value = _state.value.copy(
                    loadingState = false
                )
                onsuccess()
            }
        }
    }
}