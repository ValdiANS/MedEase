package com.myapplication.medease.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.myapplication.medease.MainActivity
import com.myapplication.medease.R
import com.myapplication.medease.data.local.entity.ScheduleEntity
import com.myapplication.medease.data.local.entity.ScheduleTimeEntity
import com.myapplication.medease.utils.EXTRA_SCHEDULE_ID
import com.myapplication.medease.utils.EXTRA_SCHEDULE_TIME_ID
import com.myapplication.medease.utils.Injection
import com.myapplication.medease.utils.NOTIFICATION_CHANNEL_ID
import com.myapplication.medease.utils.NOTIFICATION_CHANNEL_NAME
import com.myapplication.medease.utils.NOTIFICATION_ID
import com.myapplication.medease.utils.executeThread
import java.util.Calendar

class ScheduleReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        executeThread {
            val scheduleId = intent.getIntExtra(EXTRA_SCHEDULE_ID, 0)
            val scheduleTimeId = intent.getIntExtra(EXTRA_SCHEDULE_TIME_ID, 0)
            val scheduleRepository = Injection.provideScheduleRepository(context)

            Log.d("test123", "scheduleId: $scheduleId | scheduleTimeId: $scheduleTimeId")

            val schedule = scheduleRepository.getScheduleById(scheduleId)
            val scheduleTime = scheduleRepository.getScheduleTimeById(scheduleTimeId)

            showNotification(
                context = context,
                schedule = schedule,
                scheduleTime = scheduleTime
            )
        }
    }

    fun setScheduleReminder(
        context: Context,
        schedule: ScheduleEntity,
        listScheduleTime: List<ScheduleTimeEntity>,
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ScheduleReminderReceiver::class.java)


        listScheduleTime.forEach { scheduleTime ->
            intent.putExtra(EXTRA_SCHEDULE_ID, schedule.scheduleId)
            intent.putExtra(EXTRA_SCHEDULE_TIME_ID, scheduleTime.scheduleTimeId)

            val splitTime = scheduleTime.time.split(":")

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, splitTime[0].toInt())
            calendar.set(Calendar.MINUTE, splitTime[1].toInt())

            val pendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    scheduleTime.scheduleTimeId,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )

            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }

        Toast.makeText(context, "Set up alarm success", Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context, listScheduleTime: List<ScheduleTimeEntity>) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ScheduleReminderReceiver::class.java)

        listScheduleTime.forEach { scheduleTime ->
            val pendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    scheduleTime.scheduleTimeId,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )

            if (pendingIntent !== null) {
                pendingIntent.cancel()
                alarmManager.cancel(pendingIntent)
            }
        }

        Toast.makeText(context, "Alarm deleted", Toast.LENGTH_SHORT).show()
    }

    private fun showNotification(
        context: Context,
        schedule: ScheduleEntity,
        scheduleTime: ScheduleTimeEntity,
    ) {
        val notificationStyle = NotificationCompat.InboxStyle()

        notificationStyle.addLine(
            context.getString(
                R.string.time_to_take_your_medicine_descriptiion,
                scheduleTime.time,
                schedule.medicineName
            )
        )

        notificationStyle.setBigContentTitle(context.getString(R.string.take_your_medicine))

        val pendingIntent = getPendingIntent(context)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification: NotificationCompat.Builder =
            NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(context.getString(R.string.take_your_medicine))
                .setStyle(notificationStyle)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )

            notification.setChannelId(NOTIFICATION_CHANNEL_ID)

            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }


    private fun getPendingIntent(context: Context): PendingIntent? {
        val intent = Intent(context, MainActivity::class.java)

        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            } else {
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }
        }
    }
}