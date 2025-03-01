package com.afra.pennypie.data.repository

import com.afra.pennypie.data.local.dao.AccountDao
import com.afra.pennypie.data.local.entity.AccountEntity
import com.afra.pennypie.domain.model.Account
import com.afra.pennypie.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AccountRepositoryImpl(
    private val accountDao: AccountDao
) : AccountRepository {
    override fun getAllAccounts(): Flow<List<Account>> {
        return accountDao.getAllAccounts().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun getAccountById(id: Long): Account? {
        return accountDao.getAccountById(id)?.toDomainModel()
    }

    override suspend fun insertAccount(account: Account): Long {
        return accountDao.insertAccount(AccountEntity.fromDomainModel(account))
    }

    override suspend fun updateAccount(account: Account) {
        accountDao.updateAccount(AccountEntity.fromDomainModel(account))
    }

    override suspend fun deleteAccount(account: Account) {
        accountDao.deleteAccount(AccountEntity.fromDomainModel(account))
    }

    override fun getTotalBalance(): Flow<Double> {
        return accountDao.getTotalBalance().map { it ?: 0.0 }
    }

    override suspend fun updateBalance(accountId: Long, amount: Double) {
        accountDao.updateBalance(accountId, amount)
    }
} 