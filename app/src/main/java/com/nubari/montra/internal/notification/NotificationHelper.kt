package com.nubari.montra.internal.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.nubari.montra.MainActivity

object NotificationHelper {
    fun createNotification(
        context: Context,
        title: String,
        message: String,
        iconResource: Int,
        channelId: String,
        deepLinkUri: String,
        notificationId: Int

    ) {
        Log.i(
            "budget-notification",
            "notification helper method called...."
        )
        val intent = Intent(
            Intent.ACTION_VIEW,
            deepLinkUri.toUri(),
            context,
            MainActivity::class.java
        )
        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(1_121_111, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(iconResource)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .build()

        NotificationManagerCompat.from(context).notify(notificationId, notification)
        Log.i(
            "budget-notification",
            "Notification created...."
        )
    }
}