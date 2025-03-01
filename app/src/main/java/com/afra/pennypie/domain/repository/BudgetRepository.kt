package com.afra.pennypie.domain.repository

import com.afra.pennypie.domain.model.Budget
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth

interface BudgetRepository {
    fun getBudgetsByPeriod(period: YearMonth): Flow<List<Budget>>
    suspend fun getBudgetForCategory(categoryId: Long, period: YearMonth): Budget?
    suspend fun insertBudget(budget: Budget): Long
    suspend fun updateBudget(budget: Budget)
    suspend fun deleteBudget(budget: Budget)
    suspend fun updateSpentAmount(categoryId: Long, period: YearMonth, amount: Double)
    suspend fun budgetExists(categoryId: Long, period: YearMonth): Boolean
} 