package com.journalingapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.journalingapp.model.Journal
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {
    @Insert
    suspend fun createJournal(journal: Journal)

    @Query("SELECT * FROM journals ORDER BY updatedAt DESC")
    fun loadJournals(): Flow<List<Journal>>

    @Query("SELECT * FROM journals WHERE id = (:id)")
    fun findById(id: Long): Flow<Journal>

    @Update
    suspend fun updateJournal(journal: Journal)

    @Delete
    fun delete(journal: Journal)
}