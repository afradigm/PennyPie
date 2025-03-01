package com.afra.pennypie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.afra.pennypie.data.local.converter.DateTimeConverters
import com.afra.pennypie.data.local.dao.AccountDao
import com.afra.pennypie.data.local.dao.BudgetDao
import com.afra.pennypie.data.local.dao.CategoryDao
import com.afra.pennypie.data.local.dao.TransactionDao
import com.afra.pennypie.data.local.entity.AccountEntity
import com.afra.pennypie.data.local.entity.BudgetEntity
import com.afra.pennypie.data.local.entity.CategoryEntity
import com.afra.pennypie.data.local.entity.TransactionEntity

@Database(
    entities = [
        AccountEntity::class,
        CategoryEntity::class,
        TransactionEntity::class,
        BudgetEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateTimeConverters::class)
abstract class PennyPieDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
    abstract fun budgetDao(): BudgetDao
} 