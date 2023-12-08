package com.myapplication.medease.utils

import android.content.Context
import com.myapplication.medease.data.repository.AuthenticationRepository

object Injection {
    fun provideAuthenticationRepository(context: Context): AuthenticationRepository {
        return AuthenticationRepository()
    }
}