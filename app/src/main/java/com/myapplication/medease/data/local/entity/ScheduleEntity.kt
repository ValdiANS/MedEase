package com.myapplication.medease.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "schedule")
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
)

//
//@Entity(
//    tableName = "schedule_time",
//    foreignKeys = [
//        ForeignKey(
//            entity = ScheduleEntity::class,
//            onDelete = ForeignKey.CASCADE,
//            parentColumns = arrayOf("scheduleId"),
//            childColumns = arrayOf("scheduleId")
//        )
//    ],
//    indices = [Index("scheduleId")]
//)
@Entity(tableName = "schedule_time")
data class ScheduleTimeEntity(
    @PrimaryKey(autoGenerate = true)
    val scheduleTimeId: Int,

    @ColumnInfo(name = "scheduleId")
    val scheduleId: String,

    @ColumnInfo(name = "time")
    val time: String,
)

data class ScheduleWithTime(
    @Embedded
    val schedule: ScheduleEntity,

    @Relation(
        parentColumn = "scheduleId",
        entityColumn = "scheduleId",
    )
    val times: List<ScheduleTimeEntity>,
)
