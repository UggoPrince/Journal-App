package com.journal.di

import com.journal.model.Database
import com.journal.repository.JournalRepository
import org.koin.dsl.module

val dbModule = module {
    single {
        return@single Database.getDatabase(get()).journalDao()
    }
    single { JournalRepository(get()) }
}