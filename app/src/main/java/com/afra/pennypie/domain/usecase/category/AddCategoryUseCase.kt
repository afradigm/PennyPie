package com.afra.pennypie.domain.usecase.category

import com.afra.pennypie.domain.model.Category
import com.afra.pennypie.domain.repository.CategoryRepository

class AddCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(category: Category): Result<Long> = runCatching {
        if (categoryRepository.categoryExists(category.name, category.type)) {
            throw IllegalStateException("Category with name ${category.name} already exists for type ${category.type}")
        }
        categoryRepository.insertCategory(category)
    }
} 