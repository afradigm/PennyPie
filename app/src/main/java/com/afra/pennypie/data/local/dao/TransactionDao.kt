package com.afra.pennypie.data.local.dao

import androidx.room.*
import com.afra.pennypie.data.local.entity.TransactionEntity
import com.afra.pennypie.domain.model.TransactionType
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE type = :type ORDER BY date DESC")
    fun getTransactionsByType(type: TransactionType): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE accountId = :accountId ORDER BY date DESC")
    fun getTransactionsByAccount(accountId: Long): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE categoryId = :categoryId ORDER BY date DESC")
    fun getTransactionsByCategory(categoryId: Long): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getTransactionsBetweenDates(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id: Long): TransactionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity): Long

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)

    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)

    @Query("SELECT SUM(amount) FROM transactions WHERE type = :type AND date BETWEEN :startDate AND :endDate")
    fun getTotalAmountByTypeAndPeriod(type: TransactionType, startDate: LocalDateTime, endDate: LocalDateTime): Flow<Double?>
} 