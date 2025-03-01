package com.afra.pennypie.domain.usecase.account

import com.afra.pennypie.domain.model.Account
import com.afra.pennypie.domain.repository.AccountRepository

class AddAccountUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(account: Account): Result<Long> = runCatching {
        accountRepository.insertAccount(account)
    }
} 