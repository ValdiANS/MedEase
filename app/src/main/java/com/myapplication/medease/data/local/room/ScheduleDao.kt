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

    @Query("SELECT * FROM schedule WHERE scheduleId = :scheduleId")
    fun getScheduleById(scheduleId: Int): ScheduleEntity

    @Query("SELECT * FROM schedule_time")
    fun getAllScheduleTime(): LiveData<List<ScheduleTimeEntity>>

    @Query("SELECT * FROM schedule_time WHERE scheduleTimeId = :scheduleTimeId")
    fun getScheduleTimeById(scheduleTimeId: Int): ScheduleTimeEntity

    @Transaction
    @Query("SELECT * FROM schedule")
    fun getAllScheduleAndTime(): LiveData<List<ScheduleWithTime>>

    @Transaction
    @Query("SELECT * FROM schedule WHERE scheduleId = :scheduleId")
    suspend fun getScheduleAndTimesById(scheduleId: Int): ScheduleWithTime

    @Delete
    suspend fun deleteSchedule(schedule: ScheduleEntity)

    @Delete
    suspend fun deleteScheduleTime(scheduleTime: ScheduleTimeEntity)

    @Query("DELETE FROM schedule_time WHERE scheduleId = :scheduleId")
    suspend fun deleteScheduleTimeByScheduleId(scheduleId: Int)

    @Transaction
    suspend fun deleteScheduleAndTime(schedule: ScheduleEntity) {
        deleteSchedule(schedule)
        deleteScheduleTimeByScheduleId(schedule.scheduleId)
    }
}