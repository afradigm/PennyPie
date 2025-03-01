package com.afra.pennypie.domain.usecase.transaction

import com.afra.pennypie.domain.model.Transaction
import com.afra.pennypie.domain.model.TransactionType
import com.afra.pennypie.domain.repository.AccountRepository
import com.afra.pennypie.domain.repository.BudgetRepository
import com.afra.pennypie.domain.repository.TransactionRepository
import java.time.YearMonth
import kotlin.math.absoluteValue

class AddTransactionUseCase(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    private val budgetRepository: BudgetRepository
) {
    suspend operator fun invoke(transaction: Transaction): Result<Long> = runCatching {
        // Update account balance
        val amount = when (transaction.type) {
            TransactionType.INCOME -> transaction.amount
            TransactionType.EXPENSE -> -transaction.amount
            TransactionType.TRANSFER -> 0.0 // Handle transfer separately
        }
        accountRepository.updateBalance(transaction.account.id, amount)

        // Update budget spent amount if it's an expense
        if (transaction.type == TransactionType.EXPENSE) {
            val period = YearMonth.from(transaction.date)
            budgetRepository.updateSpentAmount(
                categoryId = transaction.category.id,
                period = period,
                amount = transaction.amount.absoluteValue
            )
        }

        // Insert transaction
        transactionRepository.insertTransaction(transaction)
    }
} 