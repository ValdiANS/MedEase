package com.myapplication.medease.data.remote.retrofit

import com.myapplication.medease.data.remote.response.DataItem
import com.myapplication.medease.data.remote.response.LoginResponse
import com.myapplication.medease.data.remote.response.RegisterResponse
import com.myapplication.medease.data.remote.response.MedicineResponse
import com.myapplication.medease.data.remote.response.PredictionResponse
import com.myapplication.medease.data.remote.response.ProfileByIdResponse
import com.myapplication.medease.data.remote.response.UpdateProfileResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("api/user/profile")
    suspend fun register(
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("birthdate") birthdate: String,
        @Field("phoneNumber") phoneNumber: String,
    ): RegisterResponse

    @FormUrlEncoded
    @POST("api/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): LoginResponse

    @GET("api/obat")
    suspend fun getAllMedicine() : MedicineResponse

    @GET("api/profile/user/{id}")
    suspend fun getProfileByUserId(
        @Path("id") id: String,
        @Header("token") token: String
    ) : ProfileByIdResponse

    @FormUrlEncoded
    @PUT("api/user/profile/{id}")
    suspend fun putProfileUser(
        @Path("id") id: String,
        @Header("token") token: String,
        @Field("name") name: String
    ) : UpdateProfileResponse

    @Multipart
    @POST("api/predict")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ) : PredictionResponse
}