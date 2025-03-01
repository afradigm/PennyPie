package com.afra.pennypie.data.local.dao

import androidx.room.*
import com.afra.pennypie.data.local.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Query("SELECT * FROM accounts")
    fun getAllAccounts(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM accounts WHERE id = :id")
    suspend fun getAccountById(id: Long): AccountEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: AccountEntity): Long

    @Update
    suspend fun updateAccount(account: AccountEntity)

    @Delete
    suspend fun deleteAccount(account: AccountEntity)

    @Query("SELECT SUM(balance) FROM accounts")
    fun getTotalBalance(): Flow<Double?>

    @Query("UPDATE accounts SET balance = balance + :amount WHERE id = :accountId")
    suspend fun updateBalance(accountId: Long, amount: Double)
} 