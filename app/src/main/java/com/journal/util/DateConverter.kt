package com.journal.util

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun toDate(value: Long?): Date? {
            return value?.let {
                Date(it)
            }
        }

        @TypeConverter
        @JvmStatic
        fun toTimestamp(date: Date?): Long? {
            return date?.time
        }
    }
}
