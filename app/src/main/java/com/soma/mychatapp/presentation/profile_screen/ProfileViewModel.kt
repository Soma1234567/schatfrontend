package com.soma.mychatapp.presentation.profile_screen

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soma.mychatapp.data.remote.requests.UpdateAvatar
import com.soma.mychatapp.domain.use_cases.Usecases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ProfileViewModel(
    private val usecases: Usecases
):ViewModel() {
    val _useranme = MutableStateFlow("")
    var username = _useranme.asStateFlow()
    var updateState = mutableStateOf(false)
    val _avatar = MutableStateFlow(0)
    var avatar = _avatar.asStateFlow()
    init {
        viewModelScope.launch {
            username = usecases.readUsernameUsecase().stateIn(viewModelScope)
            avatar = usecases.readAvatarUseCase().stateIn(viewModelScope)

        }
    }

    fun updateAvatar(context: Context, avatar:Int){
        viewModelScope.launch {
            updateState.value = true
            try {
                val result = usecases.updateAvatarUseCase(UpdateAvatar(username.value, avatar))
                if (result.isupdated) {
                    Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show()
                    usecases.saveAvatarUseCase(avatar)
                } else {
                    Toast.makeText(context,"Update failed.",Toast.LENGTH_SHORT).show()
                }
                updateState.value = false
            }
            catch (e: IOException){
                Toast.makeText(context,"Network error",Toast.LENGTH_SHORT).show()
                updateState.value = false
            }
            catch (e: UnknownHostException){
                Toast.makeText(context,"Please Check your internet connection",Toast.LENGTH_SHORT).show()
                updateState.value = false

            }
            catch (e: ConnectException){
                Toast.makeText(context,"Please Check your internet connection",Toast.LENGTH_SHORT).show()
                updateState.value = false

            }
            catch (e: SocketTimeoutException){
                Toast.makeText(context,"Please Check your internet connection",Toast.LENGTH_SHORT).show()
                updateState.value = false

            }

        }
    }
}