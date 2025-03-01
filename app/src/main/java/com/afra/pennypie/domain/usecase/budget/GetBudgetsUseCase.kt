package com.afra.pennypie.domain.usecase.budget

import com.afra.pennypie.domain.model.Budget
import com.afra.pennypie.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth

class GetBudgetsUseCase(
    private val budgetRepository: BudgetRepository
) {
    operator fun invoke(period: YearMonth = YearMonth.now()): Flow<List<Budget>> {
        return budgetRepository.getBudgetsByPeriod(period)
    }

    suspend fun getBudgetForCategory(categoryId: Long, period: YearMonth = YearMonth.now()): Budget? {
        return budgetRepository.getBudgetForCategory(categoryId, period)
    }
} 