package com.myapplication.medease.data.repository

import com.myapplication.medease.data.local.preference.UserModel
import com.myapplication.medease.data.local.preference.UserPreferences
import kotlinx.coroutines.flow.Flow

class AuthenticationRepository(
    private val userPreferences: UserPreferences
) {
    suspend fun login(email: String, password: String): Boolean {
        /*
        * TODO: Hit login API
        *  If login success, store user token/id to SharedPreferences and return status
        *  If login failed, return error status and message
        * */

        /*
         * TODO: This is test, delete later
         * */
        return email != "wadidaw@asd.asd" || password != "test1234"
    }

    suspend fun register(
        fullName: String,
        username: String,
        email: String,
        password: String,
        birthdate: String,
    ): Boolean {
        /*
        * TODO: Hit register API
        *  If register success, store user token/id to SharedPreferences and return status
        *  If register failed, return error status and message
        * */

        /*
         * TODO: This is test, delete later
         * */
        return email == "wadidaw@asd.asd"
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

    companion object{
        @Volatile
        private var INSTANCE: AuthenticationRepository? = null

        fun getInstance(
            userPreferences: UserPreferences
        ):AuthenticationRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AuthenticationRepository(userPreferences)
            }.also { INSTANCE = it }
    }
}