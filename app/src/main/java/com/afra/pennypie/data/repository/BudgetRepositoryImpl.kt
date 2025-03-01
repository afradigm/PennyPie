package com.afra.pennypie.data.repository

import com.afra.pennypie.data.local.dao.BudgetDao
import com.afra.pennypie.data.local.dao.CategoryDao
import com.afra.pennypie.data.local.entity.BudgetEntity
import com.afra.pennypie.domain.model.Budget
import com.afra.pennypie.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.YearMonth

class BudgetRepositoryImpl(
    private val budgetDao: BudgetDao,
    private val categoryDao: CategoryDao
) : BudgetRepository {

    private suspend fun BudgetEntity.toDomainModel(): Budget {
        val category = categoryDao.getCategoryById(categoryId)?.toDomainModel()
            ?: throw IllegalStateException("Category not found for id: $categoryId")
        
        return Budget(
            id = id,
            category = category,
            amount = amount,
            period = period,
            spent = spent
        )
    }

    private fun Budget.toEntity() = BudgetEntity(
        id = id,
        categoryId = category.id,
        amount = amount,
        period = period,
        spent = spent
    )

    override fun getBudgetsByPeriod(period: YearMonth): Flow<List<Budget>> {
        return budgetDao.getBudgetsByPeriod(period).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun getBudgetForCategory(categoryId: Long, period: YearMonth): Budget? {
        return budgetDao.getBudgetForCategory(categoryId, period)?.toDomainModel()
    }

    override suspend fun insertBudget(budget: Budget): Long {
        return budgetDao.insertBudget(budget.toEntity())
    }

    override suspend fun updateBudget(budget: Budget) {
        budgetDao.updateBudget(budget.toEntity())
    }

    override suspend fun deleteBudget(budget: Budget) {
        budgetDao.deleteBudget(budget.toEntity())
    }

    override suspend fun updateSpentAmount(categoryId: Long, period: YearMonth, amount: Double) {
        budgetDao.updateSpentAmount(categoryId, period, amount)
    }

    override suspend fun budgetExists(categoryId: Long, period: YearMonth): Boolean {
        return budgetDao.budgetExists(categoryId, period)
    }
} 