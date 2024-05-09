package com.journal.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.journal.dao.JournalDao
import com.journal.util.DateConverter

@Database(entities = [Journal::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun journalDao(): JournalDao
}