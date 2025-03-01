package com.afra.pennypie.domain.repository

import com.afra.pennypie.domain.model.Transaction
import com.afra.pennypie.domain.model.TransactionType
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface TransactionRepository {
    fun getAllTransactions(): Flow<List<Transaction>>
    fun getTransactionsByType(type: TransactionType): Flow<List<Transaction>>
    fun getTransactionsByAccount(accountId: Long): Flow<List<Transaction>>
    fun getTransactionsByCategory(categoryId: Long): Flow<List<Transaction>>
    fun getTransactionsBetweenDates(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<Transaction>>
    suspend fun getTransactionById(id: Long): Transaction?
    suspend fun insertTransaction(transaction: Transaction): Long
    suspend fun updateTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)
    fun getTotalAmountByTypeAndPeriod(type: TransactionType, startDate: LocalDateTime, endDate: LocalDateTime): Flow<Double>
} 