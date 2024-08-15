package com.soma.mychatapp.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soma.mychatapp.domain.use_cases.Usecases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val usecases: Usecases
):ViewModel() {
    private val _avatar = MutableStateFlow(0)
    var avatar = _avatar.asStateFlow()
    init {
        viewModelScope.launch {
            avatar = usecases.readAvatarUseCase().stateIn(viewModelScope)
        }
    }
}