package com.journalingapp.di

import com.journalingapp.viewmodel.JournalViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel{ JournalViewModel(get()) }
}