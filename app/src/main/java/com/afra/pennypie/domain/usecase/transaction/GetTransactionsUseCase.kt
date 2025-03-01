package com.afra.pennypie.domain.usecase.transaction

import com.afra.pennypie.domain.model.Transaction
import com.afra.pennypie.domain.model.TransactionType
import com.afra.pennypie.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

class GetTransactionsUseCase(
    private val transactionRepository: TransactionRepository
) {
    fun getAll(): Flow<List<Transaction>> {
        return transactionRepository.getAllTransactions()
    }

    fun getByType(type: TransactionType): Flow<List<Transaction>> {
        return transactionRepository.getTransactionsByType(type)
    }

    fun getByAccount(accountId: Long): Flow<List<Transaction>> {
        return transactionRepository.getTransactionsByAccount(accountId)
    }

    fun getByCategory(categoryId: Long): Flow<List<Transaction>> {
        return transactionRepository.getTransactionsByCategory(categoryId)
    }

    fun getByDateRange(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<Transaction>> {
        return transactionRepository.getTransactionsBetweenDates(startDate, endDate)
    }

    fun getTotalAmountByTypeAndPeriod(
        type: TransactionType,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): Flow<Double> {
        return transactionRepository.getTotalAmountByTypeAndPeriod(type, startDate, endDate)
    }
} 