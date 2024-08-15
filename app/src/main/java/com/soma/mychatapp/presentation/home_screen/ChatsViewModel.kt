package com.soma.mychatapp.presentation.home_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soma.mychatapp.data.local.AlreadyUsers
import com.soma.mychatapp.data.local.SingleMessage
import com.soma.mychatapp.domain.use_cases.Usecases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChatsViewModel(
    private val usecases: Usecases
):ViewModel() {
    private var _alreadyusersstate = MutableStateFlow(emptyList<SingleMessage>())
    var alreadyusersstate = _alreadyusersstate.asStateFlow()
    var _username = MutableStateFlow("")
    var username = _username.asStateFlow()
    var _unread = MutableStateFlow(emptyList<AlreadyUsers>())
    var unread  = _unread.asStateFlow()
    var _newalreadystate = MutableStateFlow(emptyList<SingleMessage>())
    var newalreadystate = _newalreadystate.asStateFlow()

    init {
        viewModelScope.launch {
            alreadyusersstate = usecases.getAlreadyChatUsersUseCase().stateIn(viewModelScope)
            username = usecases.readUsernameUsecase().stateIn(viewModelScope)
            unread = usecases.getChatUsersUseCase().stateIn(viewModelScope)
        }
    }

    fun search(context: Context, query:String){
        viewModelScope.launch {
            _newalreadystate.value = alreadyusersstate.value.filter {
                query in it.whom
            }

        }

    }
}