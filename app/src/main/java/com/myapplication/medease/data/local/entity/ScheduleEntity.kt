package com.myapplication.medease.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Entity(tableName = "schedule")
@Parcelize
data class ScheduleEntity(
    @PrimaryKey
    val scheduleId: Int,

    @ColumnInfo(name = "userId")
    val userId: String,

    @ColumnInfo(name = "medicineName")
    val medicineName: String,

    @ColumnInfo(name = "dose")
    val dose: Int,

    @ColumnInfo(name = "frequency")
    val frequency: Int,
) : Parcelable

@Entity(tableName = "schedule_time")
@Parcelize
data class ScheduleTimeEntity(
    @PrimaryKey(autoGenerate = true)
    val scheduleTimeId: Int = 0,

    @ColumnInfo(name = "scheduleId")
    val scheduleId: Int,

    @ColumnInfo(name = "time")
    val time: String,
) : Parcelable

data class ScheduleWithTime(
    @Embedded
    val schedule: ScheduleEntity,

    @Relation(
        parentColumn = "scheduleId",
        entityColumn = "scheduleId",
    )
    val times: List<ScheduleTimeEntity>,
)
