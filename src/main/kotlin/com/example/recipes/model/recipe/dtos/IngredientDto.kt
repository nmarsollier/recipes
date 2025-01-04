package com.example.recipes.model.recipe.dtos

import com.example.recipes.model.recipe.db.QuantityUnit
import kotlinx.serialization.Serializable

@Serializable
data class IngredientDto(
    val id: Int?,
    val name: String,
    val quantity: Int,
    val unit: QuantityUnit
)
