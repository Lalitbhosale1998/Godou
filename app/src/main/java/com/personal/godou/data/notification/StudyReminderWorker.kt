package com.personal.godou.data.notification

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class StudyReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            NotificationHelper.sendStudyReminderNotification(applicationContext)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
