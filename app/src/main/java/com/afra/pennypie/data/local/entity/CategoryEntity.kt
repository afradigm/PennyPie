package com.afra.pennypie.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.afra.pennypie.domain.model.Category
import com.afra.pennypie.domain.model.TransactionType

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val icon: Int,
    val color: Int,
    val type: TransactionType,
    val isDefault: Boolean = false
) {
    fun toDomainModel() = Category(
        id = id,
        name = name,
        icon = icon,
        color = color,
        type = type,
        isDefault = isDefault
    )

    companion object {
        fun fromDomainModel(category: Category) = CategoryEntity(
            id = category.id,
            name = category.name,
            icon = category.icon,
            color = category.color,
            type = category.type,
            isDefault = category.isDefault
        )
    }
} 