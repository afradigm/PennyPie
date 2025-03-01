package com.afra.pennypie.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.afra.pennypie.domain.model.Account
import com.afra.pennypie.domain.model.AccountType

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val balance: Double,
    val type: AccountType,
    val color: Int,
    val icon: Int
) {
    fun toDomainModel() = Account(
        id = id,
        name = name,
        balance = balance,
        type = type,
        color = color,
        icon = icon
    )

    companion object {
        fun fromDomainModel(account: Account) = AccountEntity(
            id = account.id,
            name = account.name,
            balance = account.balance,
            type = account.type,
            color = account.color,
            icon = account.icon
        )
    }
} 