package com.ideasapp.petemotions.presentation.util.workManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import android.util.Log

class DailyWorker(
    context: Context, workerParams: WorkerParameters
) : Worker(context, workerParams) {
    override fun doWork(): Result {
        // Здесь выполняем нужную задачу (например, отправку данных или проверку чего-либо)
        Log.d("MyWorker", "Ежедневная задача выполнена!")
        return Result.success()
    }
}