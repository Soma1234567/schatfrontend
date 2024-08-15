package com.soma.mychatapp.presentation.splash_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soma.mychatapp.domain.use_cases.Usecases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SplashViewModel(
    private val usecases: Usecases
):ViewModel() {
    private val _username = MutableStateFlow("")
    var username:StateFlow<String> = _username.asStateFlow()
    init {
        viewModelScope.launch {
            _username.value = usecases.readUsernameUsecase().stateIn(viewModelScope).value
        }
    }
}