package com.afra.pennypie.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.afra.pennypie.domain.model.RecurringPeriod
import com.afra.pennypie.domain.model.Transaction
import com.afra.pennypie.domain.model.TransactionType
import java.time.LocalDateTime

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Double,
    val type: TransactionType,
    val categoryId: Long,
    val accountId: Long,
    val date: LocalDateTime,
    val note: String = "",
    val isRecurring: Boolean = false,
    val recurringPeriod: RecurringPeriod? = null
) 