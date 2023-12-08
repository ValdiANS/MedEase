package com.myapplication.medease

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myapplication.medease.data.repository.AuthenticationRepository
import com.myapplication.medease.ui.screens.authentication.LoginFormViewModel
import com.myapplication.medease.ui.screens.authentication.RegisterFormViewModel
import com.myapplication.medease.utils.Injection

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val authenticationRepository: AuthenticationRepository,
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginFormViewModel::class.java) ->
                LoginFormViewModel(authenticationRepository) as T

            modelClass.isAssignableFrom(RegisterFormViewModel::class.java) ->
                RegisterFormViewModel(authenticationRepository) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(
                    authenticationRepository = Injection.provideAuthenticationRepository(context)
                )
            }.also { INSTANCE = it }
    }
}