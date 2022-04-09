package com.nubari.montra

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.nubari.montra.general.util.Constants.BUDGET_NOTIFICATION_CHANNEL_ID
import com.nubari.montra.general.util.Constants.BUDGET_NOTIFICATION_CHANNEL_NAME
import com.nubari.montra.general.util.Constants.BUDGET_PERIODIC_WORK_TAG
import com.nubari.montra.general.util.Constants.SUBSCRIPTION_CHANNEL_ID
import com.nubari.montra.general.util.Constants.SUBSCRIPTION_CHANNEL_NAME
import com.nubari.montra.general.util.Preferences
import com.nubari.montra.internal.workers.BudgetWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

val preferences: Preferences by lazy {
    MontraApplication.preferences!!
}

@HiltAndroidApp
class MontraApplication : Application(), Configuration.Provider {
    private val applicationScope = CoroutineScope(Dispatchers.Default)
    private val tag = "MONTRA_APPLICATION"

    companion object {
        var preferences: Preferences? = null
        lateinit var instance: MontraApplication
            private set
    }

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    private fun delayedInit() {
        Timber.tag(tag).i("delayed-init called")
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        Timber.tag(tag).d("setup recurring work called")
        val budgetRecurringWork = PeriodicWorkRequestBuilder<BudgetWorker>(
            15, TimeUnit.MINUTES
        ).addTag(BUDGET_PERIODIC_WORK_TAG).build()

        WorkManager.getInstance(applicationContext)
            .enqueue(budgetRecurringWork)
    }


    override fun onCreate() {
        super.onCreate()
        delayedInit()
        instance = this
        preferences = Preferences(applicationContext)
        // Timber setup
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
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