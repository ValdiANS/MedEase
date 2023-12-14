package com.myapplication.medease.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myapplication.medease.data.local.entity.ScheduleEntity
import com.myapplication.medease.data.local.entity.ScheduleTimeEntity

@Database(
    entities = [ScheduleEntity::class, ScheduleTimeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MedEaseDatabase : RoomDatabase() {

    abstract fun scheduleDao(): ScheduleDao

    companion object {
        @Volatile
        private var INSTANCE: MedEaseDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): MedEaseDatabase {
            if (INSTANCE == null) {
                synchronized(MedEaseDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MedEaseDatabase::class.java,
                        "medease_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            
            return INSTANCE as MedEaseDatabase
        }
    }
}