package com.nubari.montra.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nubari.montra.data.local.dao.*
import com.nubari.montra.data.local.models.*
import com.nubari.montra.data.local.typeconverters.ApplicationTypeConverters

@Database(
    entities = [
        Account::class,
        Transaction::class,
        Subscription::class,
        Category::class,
        Budget::class,
    ],
    version = 8,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 5, to = 6),
        AutoMigration(from = 6, to = 7),
        AutoMigration(from = 7, to = 8)
    ]
)
@TypeConverters(ApplicationTypeConverters::class)
abstract class MontraDatabase : RoomDatabase() {
    abstract val accountDao: AccountDao
    abstract val transactionDao: TransactionDao
    abstract val categoryDao: CategoryDao
    abstract val subscriptionDao: SubscriptionDao
    abstract val budgetDao: BudgetDao

    companion object {
        const val DATABASE_NAME = "montra_db"
    }
}