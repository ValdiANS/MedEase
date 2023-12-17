package com.myapplication.medease.ui.screens.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapplication.medease.data.local.preference.UserModel
import com.myapplication.medease.data.repository.AuthenticationRepository
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {
    private val _username = mutableStateOf("")
    val username : State<String> = _username

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _id = mutableStateOf("")
    private val _token = mutableStateOf("")
    private val _isLogin = mutableStateOf(false)
    private val _isGuest = mutableStateOf(false)


    init {
        getSession()
    }

    fun onUsernameChanged(newInput: String) {
        _username.value = newInput
    }

    fun saveChanged() {
        viewModelScope.launch {
            val response = authenticationRepository.changeNameProfile(
                _id.value,
                _token.value,
                _username.value
            )
            if (response.code == 200) {
                authenticationRepository.saveSession(
                    UserModel(
                        id = _id.value,
                        name = _username.value,
                        email = _email.value,
                        token = _token.value,
                        isLogin = _isLogin.value,
                        isGuest = _isGuest.value
                    )
                )
            }
        }
    }

    private fun getSession() {
        viewModelScope.launch {
            authenticationRepository.getSession().collect{ user ->
                _username.value = user.name
                _email.value = user.email
                _id.value = user.id
                _token.value = user.token
                _isLogin.value = user.isLogin
                _isGuest.value = user.isGuest
            }
        }
    }
}