package com.afra.pennypie.domain.model

data class Category(
    val id: Long = 0,
    val name: String,
    val icon: Int,
    val color: Int,
    val type: TransactionType,
    val isDefault: Boolean = false
) 