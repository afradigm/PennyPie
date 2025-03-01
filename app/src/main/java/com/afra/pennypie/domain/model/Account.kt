package com.afra.pennypie.domain.model

data class Account(
    val id: Long = 0,
    val name: String,
    val balance: Double,
    val type: AccountType,
    val color: Int,
    val icon: Int
)

enum class AccountType {
    CASH,
    BANK,
    CREDIT_CARD,
    SAVINGS,
    INVESTMENT,
    OTHER
} 