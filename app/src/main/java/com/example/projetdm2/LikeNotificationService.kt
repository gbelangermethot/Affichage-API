package com.example.projetdm2

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import kotlin.random.Random

class LikeNotificationService(private val context: Context) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    fun showNotification(name: String) {
        val notification = NotificationCompat.Builder(context, "notification_Like")
            .setContentTitle("Gestion des likes")
            .setContentText("Vous aimez ${name}!")
            .setSmallIcon(R.drawable.like)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }
}
