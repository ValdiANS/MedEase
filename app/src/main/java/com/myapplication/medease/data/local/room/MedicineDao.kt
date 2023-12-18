package com.myapplication.medease.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myapplication.medease.data.local.entity.MedicineEntity

@Dao
interface MedicineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentMedicine(medicineEntity: MedicineEntity)

    @Query("SELECT * FROM medicine")
    suspend fun getAllRecentMedicine(): List<MedicineEntity>
}