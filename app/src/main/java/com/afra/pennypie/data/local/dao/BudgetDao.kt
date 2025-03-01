package com.afra.pennypie.data.local.dao

import androidx.room.*
import com.afra.pennypie.data.local.entity.BudgetEntity
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budgets WHERE period = :period")
    fun getBudgetsByPeriod(period: YearMonth): Flow<List<BudgetEntity>>

    @Query("SELECT * FROM budgets WHERE categoryId = :categoryId AND period = :period")
    suspend fun getBudgetForCategory(categoryId: Long, period: YearMonth): BudgetEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budget: BudgetEntity): Long

    @Update
    suspend fun updateBudget(budget: BudgetEntity)

    @Delete
    suspend fun deleteBudget(budget: BudgetEntity)

    @Query("UPDATE budgets SET spent = spent + :amount WHERE categoryId = :categoryId AND period = :period")
    suspend fun updateSpentAmount(categoryId: Long, period: YearMonth, amount: Double)

    @Query("SELECT EXISTS(SELECT 1 FROM budgets WHERE categoryId = :categoryId AND period = :period)")
    suspend fun budgetExists(categoryId: Long, period: YearMonth): Boolean
} 