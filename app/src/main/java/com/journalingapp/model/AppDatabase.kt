package com.journalingapp.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.journalingapp.dao.JournalDao
import com.journalingapp.util.DateConverter

@Database(entities = [Journal::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun journalDao(): JournalDao
}