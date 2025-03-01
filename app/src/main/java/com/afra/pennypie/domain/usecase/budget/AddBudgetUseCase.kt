package com.afra.pennypie.domain.usecase.budget

import com.afra.pennypie.domain.model.Budget
import com.afra.pennypie.domain.repository.BudgetRepository

class AddBudgetUseCase(
    private val budgetRepository: BudgetRepository
) {
    suspend operator fun invoke(budget: Budget): Result<Long> = runCatching {
        if (budgetRepository.budgetExists(budget.category.id, budget.period)) {
            throw IllegalStateException("Budget already exists for this category and period")
        }
        budgetRepository.insertBudget(budget)
    }
} 