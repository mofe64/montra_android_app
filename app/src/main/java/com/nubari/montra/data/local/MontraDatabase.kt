package com.nubari.montra.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nubari.montra.data.local.models.Account
import com.nubari.montra.data.local.models.Category
import com.nubari.montra.data.local.models.Subscription
import com.nubari.montra.data.local.models.Transaction
import com.nubari.montra.data.local.typeconverters.ApplicationTypeConverters

@Database(
    entities = [
        Account::class,
        Transaction::class,
        Subscription::class,
        Category::class
    ],
    version = 1
)
@TypeConverters(ApplicationTypeConverters::class)
abstract class MontraDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "montra_db"
    }
}