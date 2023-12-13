package com.myapplication.medease.utils

import android.content.Context
import com.myapplication.medease.data.local.preference.UserPreferences
import com.myapplication.medease.data.local.preference.dataStore
import com.myapplication.medease.data.remote.retrofit.ApiConfig
import com.myapplication.medease.data.remote.retrofit.ApiService
import com.myapplication.medease.data.repository.AuthenticationRepository

object Injection {
    fun provideAuthenticationRepository(context: Context): AuthenticationRepository {
        val userPreferences = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()

        return AuthenticationRepository.getInstance(userPreferences, apiService)
    }
}