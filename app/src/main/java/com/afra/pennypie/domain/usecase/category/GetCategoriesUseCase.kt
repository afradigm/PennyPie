package com.afra.pennypie.domain.usecase.category

import com.afra.pennypie.domain.model.Category
import com.afra.pennypie.domain.model.TransactionType
import com.afra.pennypie.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class GetCategoriesUseCase(
    private val categoryRepository: CategoryRepository
) {
    fun getAll(): Flow<List<Category>> {
        return categoryRepository.getAllCategories()
    }

    fun getByType(type: TransactionType): Flow<List<Category>> {
        return categoryRepository.getCategoriesByType(type)
    }

    suspend fun getById(id: Long): Category? {
        return categoryRepository.getCategoryById(id)
    }
} 