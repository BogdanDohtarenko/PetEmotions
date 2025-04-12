package com.ideasapp.petemotions.presentation.util.services

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.ideasapp.petemotions.domain.use_case.calendar.AutofillPreviousDayUseCase
import javax.inject.Inject

class DailyWorkerFactory @Inject constructor (private val autofillPreviousDayUseCase:AutofillPreviousDayUseCase) : WorkerFactory() {
    override fun createWorker(
        appContext:Context,
        workerClassName: String,
        workerParameters:WorkerParameters
    ): ListenableWorker {
        return DailyWorker(autofillPreviousDayUseCase, appContext, workerParameters)
    }
}