package com.ideasapp.petemotions.data.alarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.ideasapp.petemotions.R
import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                restartAlarms(context)
            }
            "ALARM_ACTION" -> {
                // Обычный вызов будильника
                val description = intent.getStringExtra("description") ?: "No Description"
                showNotification(context, description)
            }
        }
    }

    private fun restartAlarms(context: Context) {
        val tasks = loadTasks(context)
        tasks.forEach { task ->
            context.setAlarm(task)
        }
    }

    private fun showNotification(context: Context, description: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "ALARM_CHANNEL",
                "Alarms",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, "ALARM_CHANNEL")
            .setContentTitle("Напоминание")
            .setContentText(description)
            .setSmallIcon(R.drawable.ic_timetable)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}