package com.afra.pennypie.domain.model

import java.time.YearMonth

data class Budget(
    val id: Long = 0,
    val category: Category,
    val amount: Double,
    val period: YearMonth,
    val spent: Double = 0.0
) {
    val remaining: Double
        get() = amount - spent
        
    val progress: Float
        get() = (spent / amount).toFloat().coerceIn(0f, 1f)
} 