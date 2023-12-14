package com.myapplication.medease.ui.screens.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.myapplication.medease.data.repository.AuthenticationRepository

class ProfileScreenViewModel(
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {
    private val _username = mutableStateOf("bedul")
    val username : State<String> = _username

    fun onUsernameChanged(newInput: String) {
        _username.value = newInput
    }
}