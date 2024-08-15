package com.soma.mychatapp.presentation.serachuser_screen

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soma.mychatapp.data.repository.ContactRepository
import com.soma.mychatapp.domain.use_cases.Usecases
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class SearchViewModel(
    private val contactRepository: ContactRepository
):ViewModel() {
    private val _uistate = MutableStateFlow(SearchState())
    val uistate = _uistate.asStateFlow()

    fun AllContacts(query:String){
        // When the ViewModel is first initialized, we update the `ContactUiState`
        // loading state as true while we fetch the contact list from the device.
        viewModelScope.launch {
            _uistate.update { it.copy(loadingState = true) }
            if(query.length==0){
                delay(1000)
            }
            getContacts(query)
        }
    }
    private fun getContacts(query: String) = viewModelScope.launch {
        val contacts = contactRepository.getContacts().filter { it.name.lowercase().startsWith(query.lowercase()) }
        _uistate.update { it.copy(
            loadingState = false,
            users = contacts
        ) }
    }

}