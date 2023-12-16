package com.myapplication.medease.data.repository

import com.myapplication.medease.data.local.preference.UserModel
import com.myapplication.medease.data.local.preference.UserPreferences
import com.myapplication.medease.data.remote.response.LoginResponse
import com.myapplication.medease.data.remote.response.RegisterResponse
import com.myapplication.medease.data.remote.retrofit.ApiService
import com.myapplication.medease.utils.getIdByToken
import kotlinx.coroutines.flow.Flow

class AuthenticationRepository(
    private val userPreferences: UserPreferences,
    private val apiService: ApiService,
) {
    suspend fun login(email: String, password: String): LoginResponse {
        val loginResponse = apiService.login(email, password)
        val loginData = loginResponse.data

        if (loginResponse.code == 200) {
            loginData?.token?.let { token ->
                val id = getIdByToken(token)
                id?.let {
                    val profileResponse = apiService.getProfileByUserId(id, token)
                    saveSession(
                        UserModel(
                            id = profileResponse.data.id,
                            name = profileResponse.data.name,
                            token = token,
                            isLogin = true,
                            isGuest = false
                        )
                    )
                }
            }
        }

        return loginResponse
    }

    suspend fun register(
        fullName: String,
        username: String,
        email: String,
        password: String,
        birthdate: String,
    ): RegisterResponse {
        return apiService.register(
            name = fullName,
            username = username,
            email = email,
            password = password,
            birthdate = birthdate,
            phoneNumber = ""
        )
    }

    suspend fun saveSession(userModel: UserModel) {
        userPreferences.saveSession(userModel)
    }

    fun getSession(): Flow<UserModel> {
        return userPreferences.getSession()
    }

    suspend fun logout() {
        userPreferences.logout()
    }

    companion object {
        @Volatile
        private var INSTANCE: AuthenticationRepository? = null

        fun getInstance(
            userPreferences: UserPreferences,
            apiService: ApiService,
        ): AuthenticationRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AuthenticationRepository(userPreferences, apiService)
            }.also { INSTANCE = it }
    }
}