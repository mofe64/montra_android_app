package com.nubari.montra.data.local

import androidx.room.RoomDatabase

abstract class MontraDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "montra_db"
    }
}