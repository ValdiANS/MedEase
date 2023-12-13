package com.myapplication.medease.utils

import android.content.Context
import com.myapplication.medease.data.local.preference.UserPreferences
import com.myapplication.medease.data.local.preference.dataStore
import com.myapplication.medease.data.remote.retrofit.ApiConfig
import com.myapplication.medease.data.repository.AuthenticationRepository
import com.myapplication.medease.data.repository.MedicineRepository

object Injection {
    fun provideAuthenticationRepository(context: Context): AuthenticationRepository {
        val userPreferences = UserPreferences.getInstance(context.dataStore)
        return AuthenticationRepository.getInstance(userPreferences)
    }

    fun provideMedicineRepository(context: Context): MedicineRepository {
        val apiService = ApiConfig.getApiService()
        return MedicineRepository.getInstance(apiService)
    }
}