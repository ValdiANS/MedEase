package com.myapplication.medease.data.repository

import android.util.Log
import com.myapplication.medease.data.local.entity.MedicineEntity
import com.myapplication.medease.data.local.room.MedicineDao
import com.myapplication.medease.data.remote.response.DataItem
import com.myapplication.medease.data.remote.response.ObatData
import com.myapplication.medease.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.HttpException

class MedicineRepository(
    private val apiService: ApiService,
    private val medicineDao: MedicineDao
) {
    fun getAllMedicine(): Flow<List<DataItem>> = flow {
        try {
            val medicine = apiService.getAllMedicine()
            emit(medicine.data)
        } catch (e: HttpException) {
            Log.e(TAG, "${e.message}")
        }
    }

    suspend fun insertRecentMedicine(medicineEntity: MedicineEntity) {
        medicineDao.insertRecentMedicine(medicineEntity)
    }

    fun getAllRecentMedicine() : Flow<List<MedicineEntity>> = flow {
        try {
            val medicine = medicineDao.getAllRecentMedicine()
            emit(medicine)
        } catch (e: Exception) {
            Log.e(TAG, "${e.message}")
        }
    }

    fun uploadImage(file: MultipartBody.Part) : Flow<ObatData> = flow {
//        try {
//
//        } catch (e: HttpException) {
//            Log.e(TAG, "${e.message}")
//        }
        val response = apiService.uploadImage(file)
        emit(response.data.obatData)
    }

    companion object{
        @Volatile
        private var INSTANCE: MedicineRepository? = null
        private val TAG = MedicineRepository::class.simpleName

        fun getInstance(
            apiService: ApiService,
            medicineDao: MedicineDao
        ): MedicineRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: MedicineRepository(apiService, medicineDao)
            }.also { INSTANCE = it }
    }
}