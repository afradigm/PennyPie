package com.afra.pennypie.domain.repository

import com.afra.pennypie.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getAllAccounts(): Flow<List<Account>>
    suspend fun getAccountById(id: Long): Account?
    suspend fun insertAccount(account: Account): Long
    suspend fun updateAccount(account: Account)
    suspend fun deleteAccount(account: Account)
    fun getTotalBalance(): Flow<Double>
    suspend fun updateBalance(accountId: Long, amount: Double)
} 