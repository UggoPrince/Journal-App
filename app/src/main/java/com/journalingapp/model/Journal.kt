package com.journalingapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Date

@Entity(tableName = "journals")
data class Journal(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "body") var body: String,
    @ColumnInfo(name = "date") var date: Date,
    @ColumnInfo(name = "updatedAt") var updatedAt: Date
): Serializable {
    @Ignore var isChecked: Boolean = false
}
