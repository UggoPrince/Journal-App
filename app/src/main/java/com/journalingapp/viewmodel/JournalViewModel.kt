package com.journalingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journalingapp.model.Journal
import com.journalingapp.repository.JournalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JournalViewModel(private val journalRepository: JournalRepository) : ViewModel() {
    var allJournals: Flow<List<Journal>> = journalRepository.getAllJournals()

    fun retrieveAllJournals(): Flow<List<Journal>> {
        return journalRepository.getAllJournals().distinctUntilChanged()
    }

    fun addJournal(journal: Journal) {
        viewModelScope.launch {
            journalRepository.insert(journal)
        }
    }

    fun getJournal(id: Long): Flow<Journal> {
        return journalRepository.getJournal(id)
    }

    fun updateJournal(journal: Journal) {
        viewModelScope.launch {
            journalRepository.update(journal)
        }
    }

    fun deleteJournal(journal: Journal) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                journalRepository.delete(journal)
            }
        }
    }
}
