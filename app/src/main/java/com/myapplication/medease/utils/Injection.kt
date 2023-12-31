package com.myapplication.medease.utils

import android.content.Context
import com.myapplication.medease.data.local.preference.UserPreferences
import com.myapplication.medease.data.local.preference.dataStore
import com.myapplication.medease.data.local.room.MedEaseDatabase
import com.myapplication.medease.data.remote.retrofit.ApiConfig
import com.myapplication.medease.data.repository.AuthenticationRepository
import com.myapplication.medease.data.repository.MedicineRepository
import com.myapplication.medease.data.repository.ScheduleRepository

object Injection {
    fun provideAuthenticationRepository(context: Context): AuthenticationRepository {
        val userPreferences = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()

        return AuthenticationRepository.getInstance(userPreferences, apiService)
    }

    fun provideMedicineRepository(context: Context): MedicineRepository {
        val apiService = ApiConfig.getApiService()
        val medEaseDb = MedEaseDatabase.getInstance(context)
        val medicineDao = medEaseDb.MedicineDao()
        return MedicineRepository.getInstance(apiService, medicineDao)
    }

    fun provideScheduleRepository(context: Context): ScheduleRepository {
        val userPreferences = UserPreferences.getInstance(context.dataStore)
        val medEaseDb = MedEaseDatabase.getInstance(context)
        val scheduleDao = medEaseDb.scheduleDao()

        return ScheduleRepository.getInstance(userPreferences, scheduleDao)
    }
}