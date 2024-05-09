package com.journal.model

import android.content.Context
import androidx.room.Room

class Database {
    companion object {
        fun getDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "journal_database"
        ).build()
    }
}