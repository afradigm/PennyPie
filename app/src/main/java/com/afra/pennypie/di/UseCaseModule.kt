package com.afra.pennypie.di

import com.afra.pennypie.domain.usecase.account.AddAccountUseCase
import com.afra.pennypie.domain.usecase.account.GetAccountsUseCase
import com.afra.pennypie.domain.usecase.budget.AddBudgetUseCase
import com.afra.pennypie.domain.usecase.budget.GetBudgetsUseCase
import com.afra.pennypie.domain.usecase.category.AddCategoryUseCase
import com.afra.pennypie.domain.usecase.category.GetCategoriesUseCase
import com.afra.pennypie.domain.usecase.transaction.AddTransactionUseCase
import com.afra.pennypie.domain.usecase.transaction.GetTransactionsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    // Account use cases
    single { GetAccountsUseCase(get()) }
    single { AddAccountUseCase(get()) }

    // Transaction use cases
    single { GetTransactionsUseCase(get()) }
    single { AddTransactionUseCase(get(), get(), get()) }

    // Budget use cases
    single { GetBudgetsUseCase(get()) }
    single { AddBudgetUseCase(get()) }

    // Category use cases
    single { GetCategoriesUseCase(get()) }
    single { AddCategoryUseCase(get()) }
} 