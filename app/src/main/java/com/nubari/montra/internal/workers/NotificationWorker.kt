package com.nubari.montra.internal.workers

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.nubari.montra.R
import com.nubari.montra.internal.notification.NotificationHelper

class NotificationWorker(
    private val context: Context,
    private val workerParams: WorkerParameters
) : Worker(context, workerParams) {
    @SuppressLint("LongLogTag")
    override fun doWork(): Result {
        Log.i(
            "budget-notification",
            "Work request received ..., creating notification"
        )
        Log.i(
            "budget-notification-worker-input-data",
            inputData.toString()
        )
        val notificationParams = inputData.keyValueMap.keys
        if (!notificationParams.contains("title")
            || !notificationParams.contains("message")
            || !notificationParams.contains("iconResource")
            || !notificationParams.contains("channelId")
            || !notificationParams.contains("deepLinkUri")
            || !notificationParams.contains("notificationId")

        ) {
            return Result.failure(
                workDataOf(
                    "cause" to "Required field missing"
                )
            )
        }
        NotificationHelper.createNotification(
            context = context,
            title = inputData.getString("title").toString(),
            message = inputData.getString("message").toString(),
            iconResource = inputData.getInt("iconResource", R.drawable.wallet_3),
            channelId = inputData.getString("channelId").toString(),
            deepLinkUri = inputData.getString("deepLinkUri").toString(),
            notificationId = inputData.getInt("notificationId", -1)
        )
        Log.i(
            "budget-notification",
            "Work request completed ...."
        )
        return Result.success()

    }


}