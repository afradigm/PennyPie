package com.afra.pennypie.data.repository

import com.afra.pennypie.data.local.dao.AccountDao
import com.afra.pennypie.data.local.dao.CategoryDao
import com.afra.pennypie.data.local.dao.TransactionDao
import com.afra.pennypie.data.local.entity.TransactionEntity
import com.afra.pennypie.domain.model.Transaction
import com.afra.pennypie.domain.model.TransactionType
import com.afra.pennypie.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

class TransactionRepositoryImpl(
    private val transactionDao: TransactionDao,
    private val accountDao: AccountDao,
    private val categoryDao: CategoryDao
) : TransactionRepository {

    private suspend fun TransactionEntity.toDomainModel(): Transaction {
        val account = accountDao.getAccountById(accountId)?.toDomainModel()
            ?: throw IllegalStateException("Account not found for id: $accountId")
        val category = categoryDao.getCategoryById(categoryId)?.toDomainModel()
            ?: throw IllegalStateException("Category not found for id: $categoryId")
        
        return Transaction(
            id = id,
            amount = amount,
            type = type,
            category = category,
            account = account,
            date = date,
            note = note,
            isRecurring = isRecurring,
            recurringPeriod = recurringPeriod
        )
    }

    private fun Transaction.toEntity() = TransactionEntity(
        id = id,
        amount = amount,
        type = type,
        categoryId = category.id,
        accountId = account.id,
        date = date,
        note = note,
        isRecurring = isRecurring,
        recurringPeriod = recurringPeriod
    )

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getTransactionsByType(type: TransactionType): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByType(type).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getTransactionsByAccount(accountId: Long): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByAccount(accountId).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getTransactionsByCategory(categoryId: Long): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByCategory(categoryId).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getTransactionsBetweenDates(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): Flow<List<Transaction>> {
        return transactionDao.getTransactionsBetweenDates(startDate, endDate).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun getTransactionById(id: Long): Transaction? {
        return transactionDao.getTransactionById(id)?.toDomainModel()
    }

    override suspend fun insertTransaction(transaction: Transaction): Long {
        return transactionDao.insertTransaction(transaction.toEntity())
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction.toEntity())
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction.toEntity())
    }

    override fun getTotalAmountByTypeAndPeriod(
        type: TransactionType,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): Flow<Double> {
        return transactionDao.getTotalAmountByTypeAndPeriod(type, startDate, endDate)
            .map { it ?: 0.0 }
    }
} 