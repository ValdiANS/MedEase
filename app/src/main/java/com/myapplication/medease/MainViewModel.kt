package com.myapplication.medease

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

    fun saveSession(userModel: UserModel) {
        viewModelScope.launch {
            authenticationRepository.saveSession(userModel)
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