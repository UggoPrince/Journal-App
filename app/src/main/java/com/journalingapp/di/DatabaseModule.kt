package com.journalingapp.di

import com.journalingapp.model.Database
import com.journalingapp.repository.JournalRepository
import org.koin.dsl.module

val dbModule = module {
    single {
        return@single Database.getDatabase(get()).journalDao()
    }
    single { JournalRepository(get()) }
}