package com.afra.pennypie.domain.usecase.account

import com.afra.pennypie.domain.model.Account
import com.afra.pennypie.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow

class GetAccountsUseCase(
    private val accountRepository: AccountRepository
) {
    operator fun invoke(): Flow<List<Account>> {
        return accountRepository.getAllAccounts()
    }
} 