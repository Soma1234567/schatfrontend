package com.soma.mychatapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soma.mychatapp.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MySplashViewModel(
    private val repository: Repository
):ViewModel() {

    var phoneNumber = MutableStateFlow("")
    init {
        viewModelScope.launch {
            phoneNumber.value = repository.readUsername().stateIn(viewModelScope).value
        }
    }
}