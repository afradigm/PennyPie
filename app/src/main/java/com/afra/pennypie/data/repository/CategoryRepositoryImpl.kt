package com.afra.pennypie.data.repository

import com.afra.pennypie.data.local.dao.CategoryDao
import com.afra.pennypie.data.local.entity.CategoryEntity
import com.afra.pennypie.domain.model.Category
import com.afra.pennypie.domain.model.TransactionType
import com.afra.pennypie.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao
) : CategoryRepository {
    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getCategoriesByType(type: TransactionType): Flow<List<Category>> {
        return categoryDao.getCategoriesByType(type).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun getCategoryById(id: Long): Category? {
        return categoryDao.getCategoryById(id)?.toDomainModel()
    }

    override suspend fun insertCategory(category: Category): Long {
        return categoryDao.insertCategory(CategoryEntity.fromDomainModel(category))
    }

    override suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(CategoryEntity.fromDomainModel(category))
    }

    override suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(CategoryEntity.fromDomainModel(category))
    }

    override suspend fun categoryExists(name: String, type: TransactionType): Boolean {
        return categoryDao.categoryExists(name, type)
    }
} 