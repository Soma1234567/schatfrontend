package com.soma.mychatapp.presentation.serachuser_screen

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soma.mychatapp.domain.use_cases.Usecases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class SearchViewModel(
    private val usecases: Usecases
):ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()
    private var _username = MutableStateFlow("")
    val username = _username.asStateFlow()
    init {
        viewModelScope.launch {
            _username.value = usecases.readUsernameUsecase().stateIn(viewModelScope).value
        }
    }
    fun find(context: Context, query:String){
        viewModelScope.launch {
            _state.value = _state.value.copy(
                loadingState = true
            )
            try {
                val result = usecases.fetchUsersUseCase(query)
                _state.value = _state.value.copy(
                    loadingState = false,
                    users = result,
                    size = result.size
                )
            }
            catch (e: IOException){
                Toast.makeText(context,"Network error", Toast.LENGTH_SHORT).show()
                _state.value = _state.value.copy(loadingState = false)
            }
            catch (e: UnknownHostException){
                Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show()
                _state.value = _state.value.copy(loadingState = false)
            }
            catch (e: ConnectException){
                Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show()
                _state.value = _state.value.copy(loadingState = false)
            }
            catch (e: SocketTimeoutException){
                Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show()
                _state.value = _state.value.copy(loadingState = false)
            }

        }
    }
}