package com.example.projetdm2

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

class LikeNotification: Application() {
    override fun onCreate() {
        super.onCreate()
        val notificationChannel = NotificationChannel(
            "notification_Like",
            "Like",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
}