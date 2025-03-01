package com.afra.pennypie.data.local.dao

import androidx.room.*
import com.afra.pennypie.data.local.entity.CategoryEntity
import com.afra.pennypie.domain.model.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE type = :type")
    fun getCategoriesByType(type: TransactionType): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: Long): CategoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity): Long

    @Update
    suspend fun updateCategory(category: CategoryEntity)

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM categories WHERE name = :name AND type = :type LIMIT 1)")
    suspend fun categoryExists(name: String, type: TransactionType): Boolean
} 