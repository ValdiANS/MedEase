package com.myapplication.medease.utils

import java.util.concurrent.Executors

const val NOTIFICATION_CHANNEL_NAME = "MedEase Schedule Channel"
const val NOTIFICATION_CHANNEL_ID = "notify-schedule"
const val NOTIFICATION_ID = 64
//const val ID_REPEATING = 202

private val SINGLE_EXECUTOR = Executors.newSingleThreadExecutor()

fun executeThread(f: () -> Unit) {
    SINGLE_EXECUTOR.execute(f)
}

const val EXTRA_SCHEDULE_ID = "EXTRA_SCHEDULE_ID"
const val EXTRA_SCHEDULE_TIME_ID = "EXTRA_SCHEDULE_TIME_ID"