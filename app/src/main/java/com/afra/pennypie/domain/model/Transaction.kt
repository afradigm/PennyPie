package com.afra.pennypie.domain.model

import java.time.LocalDateTime

data class Transaction(
    val id: Long = 0,
    val amount: Double,
    val type: TransactionType,
    val category: Category,
    val account: Account,
    val date: LocalDateTime,
    val note: String = "",
    val isRecurring: Boolean = false,
    val recurringPeriod: RecurringPeriod? = null
)

enum class TransactionType {
    INCOME,
    EXPENSE,
    TRANSFER
}

enum class RecurringPeriod {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
} 