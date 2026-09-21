package com.personal.godou.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.personal.godou.MainActivity
import com.personal.godou.R

object NotificationHelper {

    const val CHANNEL_ID = "godou_study_reminder_channel"
    private const val CHANNEL_NAME = "学習リマインダー通知"
    private const val NOTIFICATION_ID = 1001

    fun createNotificationChannel(context: Context) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "毎日の日本語単語学習およびストリーク保護リマインダー通知"
            enableVibration(true)
        }
        manager.createNotificationChannel(channel)
    }

    fun sendStudyReminderNotification(context: Context, customMessage: String? = null) {
        createNotificationChannel(context)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("📚 今日の単語学習の時間です！")
            .setContentText(customMessage ?: "1日の目標単語を復習して、連続学習ストリークを維持しましょう！")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)
    }
}
