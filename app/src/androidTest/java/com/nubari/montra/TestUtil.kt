package com.nubari.montra

import com.nubari.montra.data.local.models.Budget
import com.nubari.montra.data.local.models.Category
import com.nubari.montra.data.local.models.Subscription
import com.nubari.montra.data.local.models.enums.BudgetType
import com.nubari.montra.data.local.models.enums.SubscriptionType
import java.math.BigDecimal
import java.util.*

object TestUtil {
    fun generateMultipleBudgets(count: Int): List<Budget> {
        val budgets: MutableList<Budget> = mutableListOf();
        val possibleBudgetTypes = listOf(
            BudgetType.CATEGORY,
            BudgetType.GENERAL
        )
        val possibleBigDecimalValues = listOf(
            BigDecimal.ZERO,
            BigDecimal.ONE,
            BigDecimal.TEN,
            BigDecimal.valueOf(100),
            BigDecimal(100_000),
            BigDecimal(100_000_000),

            )
        for (i in 1..count) {
            val budget = Budget(
                "budget$i",
                possibleBudgetTypes.random(),
                shouldNotify = true,
                100,
                exceeded = false,
                Date(),
                possibleBigDecimalValues.random(),
                possibleBigDecimalValues.random(),
                "testCategoryId$i",
                "Food$i",
                "test123",
                Date()
            )
            budgets.add(budget)
        }
        return budgets;
    }

    fun generateMultipleCategories(count: Int): List<Category> {
        val categories = mutableListOf<Category>();
        for (i in 1..count) {
            val category = Category(
                id = i.toString(),
                name = "category-$i",
                iconName = "icon-name-$i"
            )
            categories.add(category)
        }
        return categories
    }

    fun generateMultipleSubscriptions(count: Int): List<Subscription> {
        val subs = mutableListOf<Subscription>()
        val possibleSubTypes = mutableListOf(
            SubscriptionType.DAILY,
            SubscriptionType.MONTHLY,
            SubscriptionType.QUARTERLY,
            SubscriptionType.WEEKLY,
            SubscriptionType.YEARLY
        )
        for (i in 1..count) {
            val sub = Subscription(
                id = i.toString(),
                name = "sub-$i",
                accountId = "accountId",
                dueDate = Date(),
                type = possibleSubTypes.random()
            )
            subs.add(sub)
        }
        return subs
    }
}