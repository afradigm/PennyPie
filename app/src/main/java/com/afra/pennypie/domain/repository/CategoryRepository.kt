package com.afra.pennypie.domain.repository

import com.afra.pennypie.domain.model.Category
import com.afra.pennypie.domain.model.TransactionType
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategories(): Flow<List<Category>>
    fun getCategoriesByType(type: TransactionType): Flow<List<Category>>
    suspend fun getCategoryById(id: Long): Category?
    suspend fun insertCategory(category: Category): Long
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategory(category: Category)
    suspend fun categoryExists(name: String, type: TransactionType): Boolean
} 