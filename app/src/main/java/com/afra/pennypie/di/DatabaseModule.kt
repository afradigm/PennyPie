package com.afra.pennypie.di

import androidx.room.Room
import com.afra.pennypie.data.local.PennyPieDatabase
import com.afra.pennypie.data.repository.AccountRepositoryImpl
import com.afra.pennypie.data.repository.BudgetRepositoryImpl
import com.afra.pennypie.data.repository.CategoryRepositoryImpl
import com.afra.pennypie.data.repository.TransactionRepositoryImpl
import com.afra.pennypie.domain.repository.AccountRepository
import com.afra.pennypie.domain.repository.BudgetRepository
import com.afra.pennypie.domain.repository.CategoryRepository
import com.afra.pennypie.domain.repository.TransactionRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            PennyPieDatabase::class.java,
            "penny_pie.db"
        ).build()
    }

    single { get<PennyPieDatabase>().accountDao() }
    single { get<PennyPieDatabase>().categoryDao() }
    single { get<PennyPieDatabase>().transactionDao() }
    single { get<PennyPieDatabase>().budgetDao() }

    single<AccountRepository> { AccountRepositoryImpl(get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
    single<TransactionRepository> { TransactionRepositoryImpl(get(), get(), get()) }
    single<BudgetRepository> { BudgetRepositoryImpl(get(), get()) }
} 