package com.myapplication.medease.data.remote.retrofit

import com.myapplication.medease.data.remote.response.AllMedicineResponse
import retrofit2.http.GET

interface ApiService {
    @GET("api/obat")
    suspend fun getAllMedicine() : AllMedicineResponse
}