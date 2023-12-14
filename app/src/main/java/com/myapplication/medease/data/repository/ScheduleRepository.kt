package com.myapplication.medease.data.repository

import androidx.lifecycle.LiveData
import com.myapplication.medease.data.local.entity.ScheduleEntity
import com.myapplication.medease.data.local.entity.ScheduleTimeEntity
import com.myapplication.medease.data.local.entity.ScheduleWithTime
import com.myapplication.medease.data.local.room.ScheduleDao

class ScheduleRepository(
    private val scheduleDao: ScheduleDao,
) {
    fun getAllScheduleAndTime(): LiveData<List<ScheduleWithTime>> =
        scheduleDao.getAllScheduleAndTime()

    suspend fun insertSchedule(schedule: ScheduleEntity) {
        scheduleDao.insertSchedule(schedule)
    }

    suspend fun insertScheduleTime(scheduleTime: ScheduleTimeEntity) {
        scheduleDao.insertScheduleTime(scheduleTime)
    }

    fun deleteSchedule(schedule: ScheduleEntity) {
        scheduleDao.deleteSchedule(schedule)
    }

    companion object {
        @Volatile
        private var INSTANCE: ScheduleRepository? = null

        fun getInstance(
            scheduleDao: ScheduleDao,
        ): ScheduleRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ScheduleRepository(scheduleDao)
            }.also { INSTANCE = it }
    }
}