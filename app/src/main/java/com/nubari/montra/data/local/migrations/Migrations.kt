package com.nubari.montra.data.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE 'transactions' ADD COLUMN 'transaction_name' TEXT NOT NULL")
        database.execSQL("ALTER TABLE 'transactions' ADD COLUMN 'transaction_description' TEXT NOT NULL ")
        database.execSQL("ALTER TABLE 'transactions' ADD COLUMN 'attachment_local' TEXT")
        database.execSQL("ALTER TABLE 'transactions' ADD COLUMN 'attachment_remote' TEXT")
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE 'transactions' ADD COLUMN 'transaction_frequency' TEXT")
    }
}

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE 'Category' ADD COLUMN 'icon_name' TEXT NOT NULL")
    }
}