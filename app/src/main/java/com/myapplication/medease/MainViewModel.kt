package com.myapplication.medease

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.myapplication.medease.data.local.preference.UserModel
import com.myapplication.medease.data.repository.AuthenticationRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val authenticationRepository: AuthenticationRepository
): ViewModel() {

    private val _timeoutSplash = mutableStateOf(false)
    val timeoutSplash: State<Boolean> get() = _timeoutSplash

    fun setTimeoutSplash(isTimeout: Boolean) {
        _timeoutSplash.value = isTimeout
    }

    fun loginAsGuest() {
        val user = UserModel(
            "",
            "",
            "",
            "",
            false,
            true
        )
        viewModelScope.launch {
            authenticationRepository.saveSession(user)
        }
    }

    fun getSession() : LiveData<UserModel> {
        return authenticationRepository.getSession().asLiveData()
    }

    fun onLogOut() {
        viewModelScope.launch {
            authenticationRepository.logout()
        }
    }
}