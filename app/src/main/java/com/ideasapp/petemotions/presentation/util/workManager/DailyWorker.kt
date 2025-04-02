package com.ideasapp.petemotions.presentation.util.workManager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import android.util.Log
import androidx.core.app.NotificationCompat
import com.ideasapp.petemotions.R
import com.ideasapp.petemotions.domain.use_case.calendar.AutofillPreviousDayUseCase
import com.ideasapp.petemotions.domain.use_case.calendar.GetCalendarUseCase

class DailyWorker (
    private val autofillPreviousDayUseCase: AutofillPreviousDayUseCase,
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        return try {
            autofillPreviousDayUseCase()

            showNotification(
                context = applicationContext,
                title = "We keep your calendar",
                message = "Day was filled"
            )

            Result.success()
        } catch (e: Exception) {
            showNotification(
                context = applicationContext,
                title = "Sorry :(",
                message = "We can't fill yesterday"
            )
            Result.retry()
        }
    }

    private fun showNotification(context: Context, title: String, message: String) {
        val channelId = "1"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "DayFilling",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_filling)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(1, notification)
    }
}
