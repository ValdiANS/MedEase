package com.myapplication.medease.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.myapplication.medease.data.local.entity.ScheduleEntity
import com.myapplication.medease.data.local.entity.ScheduleTimeEntity
import com.myapplication.medease.data.local.entity.ScheduleWithTime

@Dao
interface ScheduleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSchedule(schedule: ScheduleEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertScheduleTime(scheduleTime: ScheduleTimeEntity)

    @Query("SELECT * FROM schedule")
    fun getAllSchedule(): LiveData<List<ScheduleEntity>>

    @Query("SELECT * FROM schedule_time")
    fun getAllScheduleTime(): LiveData<List<ScheduleTimeEntity>>

    @Transaction
    @Query("SELECT * FROM schedule")
    fun getAllScheduleAndTime(): LiveData<List<ScheduleWithTime>>

    @Transaction
    @Query("SELECT * FROM schedule WHERE scheduleId = :scheduleId")
    fun getScheduleAndTimesById(scheduleId: Int): LiveData<ScheduleWithTime>

    @Delete
    fun deleteSchedule(schedule: ScheduleEntity)

    @Delete
    fun deleteScheduleTime(scheduleTime: ScheduleTimeEntity)
}