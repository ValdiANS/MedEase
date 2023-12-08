package com.myapplication.medease.data.repository

class AuthenticationRepository {
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
}