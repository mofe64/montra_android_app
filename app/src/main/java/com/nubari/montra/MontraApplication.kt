package com.nubari.montra

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.nubari.montra.general.util.Constants.BUDGET_NOTIFICATION_CHANNEL_ID
import com.nubari.montra.general.util.Constants.BUDGET_NOTIFICATION_CHANNEL_NAME
import com.nubari.montra.general.util.Constants.SUBSCRIPTION_CHANNEL_ID
import com.nubari.montra.general.util.Constants.SUBSCRIPTION_CHANNEL_NAME
import com.nubari.montra.general.util.Preferences
import dagger.hilt.android.HiltAndroidApp

val preferences: Preferences by lazy {
    MontraApplication.preferences!!
}

@HiltAndroidApp
class MontraApplication : Application() {
    companion object {
        var preferences: Preferences? = null
        lateinit var instance: MontraApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        preferences = Preferences(applicationContext)
        // We create a notification channel if build version is
        // greater than oreo, before oreo no need to create
        // notification channels
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val budgetNotificationChannel = NotificationChannel(
                BUDGET_NOTIFICATION_CHANNEL_ID,
                BUDGET_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val subscriptionNotificationChannel = NotificationChannel(
                SUBSCRIPTION_CHANNEL_ID,
                SUBSCRIPTION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )

            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannels(
                listOf(budgetNotificationChannel, subscriptionNotificationChannel)
            )
        }
    }
}