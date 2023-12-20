package com.myapplication.medease.data.repository

import androidx.lifecycle.LiveData
import com.myapplication.medease.data.local.entity.ScheduleEntity
import com.myapplication.medease.data.local.entity.ScheduleTimeEntity
import com.myapplication.medease.data.local.entity.ScheduleWithTime
import com.myapplication.medease.data.local.preference.UserModel
import com.myapplication.medease.data.local.preference.UserPreferences
import com.myapplication.medease.data.local.room.ScheduleDao
import kotlinx.coroutines.flow.Flow

class ScheduleRepository(
    private val userPreferences: UserPreferences,
    private val scheduleDao: ScheduleDao,
) {
    fun getAllScheduleAndTime(): LiveData<List<ScheduleWithTime>> =
        scheduleDao.getAllScheduleAndTime()

    suspend fun getScheduleAndTimesById(scheduleId: Int) = scheduleDao.getScheduleAndTimesById(scheduleId)

    fun getScheduleById(scheduleId: Int) = scheduleDao.getScheduleById(scheduleId)

    fun getScheduleTimeById(scheduleTimeId: Int) = scheduleDao.getScheduleTimeById(scheduleTimeId)

    suspend fun insertSchedule(schedule: ScheduleEntity) {
        scheduleDao.insertSchedule(schedule)
    }

    suspend fun insertScheduleTime(scheduleTime: ScheduleTimeEntity) {
        scheduleDao.insertScheduleTime(scheduleTime)
    }

    suspend fun deleteSchedule(schedule: ScheduleEntity) {
        scheduleDao.deleteScheduleAndTime(schedule)
    }

    fun getSession(): Flow<UserModel> {
        return userPreferences.getSession()
    }

    companion object {
        @Volatile
        private var INSTANCE: ScheduleRepository? = null

        fun getInstance(
            userPreferences: UserPreferences,
            scheduleDao: ScheduleDao,
        ): ScheduleRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ScheduleRepository(userPreferences, scheduleDao)
            }.also { INSTANCE = it }
    }
}