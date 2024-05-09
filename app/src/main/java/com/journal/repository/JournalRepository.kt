package com.journal.repository

import com.journal.dao.JournalDao
import com.journal.model.Journal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

class JournalRepository(private val journalDao: JournalDao) {
    fun getAllJournals(): Flow<List<Journal>> {
        return journalDao.loadJournals().distinctUntilChanged()
    }

    suspend fun insert(journal: Journal) {
        journalDao.createJournal(journal)
    }

    fun getJournal(id: Long): Flow<Journal> {
        return journalDao.findById(id).distinctUntilChanged()
    }

    suspend fun update(journal: Journal) {
        journalDao.updateJournal(journal)
    }

    fun delete(journal: Journal) {
        journalDao.delete(journal)
    }
}