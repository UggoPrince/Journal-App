package com.journal.di

import com.journal.viewmodel.JournalViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel{JournalViewModel(get())}
}