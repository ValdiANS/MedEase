package com.myapplication.medease.data.remote.retrofit

import com.myapplication.medease.data.remote.response.LoginResponse
import com.myapplication.medease.data.remote.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("/api/register")
    suspend fun register(
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("birthdate") birthdate: String,
        @Field("phoneNumber") phoneNumber: String,
    ): RegisterResponse

    @FormUrlEncoded
    @POST("/api/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): LoginResponse

}