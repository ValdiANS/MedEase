package com.myapplication.medease.data.repository

import android.util.Log
import com.myapplication.medease.data.remote.response.DataItem
import com.myapplication.medease.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class MedicineRepository(
    private val apiService: ApiService,
) {
    fun getAllMedicine(): Flow<List<DataItem>> = flow {
        try {
            val medicine = apiService.getAllMedicine()
            emit(medicine.data)
        } catch (e: HttpException) {
            Log.e(TAG, "${e.message}")
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: MedicineRepository? = null
        private val TAG = MedicineRepository::class.simpleName

        fun getInstance(
            apiService: ApiService
        ): MedicineRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: MedicineRepository(apiService)
            }.also { INSTANCE = it }
    }
}