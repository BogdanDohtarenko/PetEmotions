package com.ideasapp.petemotions.presentation.util.workManager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import com.ideasapp.petemotions.R
import com.ideasapp.petemotions.domain.use_case.calendar.AutofillPreviousDayUseCase
import com.ideasapp.petemotions.domain.use_case.calendar.GetCalendarUseCase
import kotlinx.coroutines.runBlocking

class DailyWorker (
    private val autofillPreviousDayUseCase: AutofillPreviousDayUseCase,
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("AutoFill", "DailyWorker started")
        return try {
            autofillPreviousDayUseCase()
            showNotification(
                context = applicationContext,
                title = "We keep your calendar",
                message = "Yesterday was filled"
            )
            Log.d("AutoFill", "DailyWorker completed successfully")
            Result.success()
        } catch (e: Exception) {
            Log.e("AutoFill", "DailyWorker failed", e)
            showNotification(
                context = applicationContext,
                title = "Sorry :(",
                message = "We can't fill yesterday"
            )
            Result.failure()
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
