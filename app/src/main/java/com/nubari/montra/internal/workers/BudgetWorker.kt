package com.nubari.montra.internal.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.nubari.montra.domain.repository.BudgetRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.math.BigDecimal
import java.util.*

@HiltWorker
class BudgetWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val budgetRepository: BudgetRepository
) : CoroutineWorker(context, workerParams) {
    private val tag = "Budget_Worker"
    override suspend fun doWork(): Result {
        Timber.tag(tag).i("Budget worker do work called")
        val calender = Calendar.getInstance(Locale.getDefault())
        val currentMonthDay = calender.get(Calendar.DAY_OF_MONTH)
        Timber.tag(tag).i("day of month is $currentMonthDay")
        if (currentMonthDay != 1) {
            Timber.tag(tag).d("Current day of month does not match")
            Timber.tag(tag).d("Returning Result failure")
            return Result.failure(
                workDataOf(
                    "status" to "failure",
                    "cause" to "Day of month is not first"
                )
            )
        }
        try {
            Timber.tag(tag).i("Budget reset operation started")
            var allBudgets = budgetRepository.getAllExistingBudgets();
            allBudgets = allBudgets.map {
                it.spend = BigDecimal.ZERO
                it.exceeded = false
                return@map it
            }
            budgetRepository.saveBudget(
                *(allBudgets.toTypedArray())
            )
            return Result.success(
                workDataOf(
                    "status" to "success"
                )
            )
        } catch (e: Exception) {
            Timber.tag(tag).d("Exception occurred during budget reset op")
            Timber.tag(tag).e(e)
            return Result.failure(
                workDataOf(
                    "status" to "failure",
                    "cause" to e.localizedMessage
                )
            )
        }

    }

}