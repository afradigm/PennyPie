package com.afra.pennypie.di

import com.afra.pennypie.presentation.home.HomeViewModel
import com.afra.pennypie.presentation.transactions.TransactionsViewModel
import com.afra.pennypie.presentation.transactions.add.AddTransactionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { TransactionsViewModel(get()) }
    viewModel { AddTransactionViewModel(get(), get(), get()) }
    // Add other ViewModels here
} 