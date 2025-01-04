package com.example.recipes.model.recipe.dtos

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
data class NewRecipeDto(
    @field:NotBlank(message = "field_cannot_be_empty")
    @field:Size(min = 5, max = 100, message = "field_length_not_within_bounds")
    val title: String,
    @field:NotBlank(message = "field_cannot_be_empty")
    @field:Size(min = 5, max = 1000, message = "field_length_not_within_bounds")
    val description: String,
    @field:NotBlank(message = "field_cannot_be_empty")
    @field:Size(min = 5, max = 100, message = "field_length_not_within_bounds")
    val instructions: String,
    val minutes: Int,
    @field:NotBlank(message = "field_cannot_be_empty")
    val difficulty: String,
    val vegetarian: Boolean,
    @field:Min(value = 1, message = "cannot_be_less_than_one")
    val servings: Int,
    val ingredients: List<IngredientDto> = emptyList(),
    @field:Min(value = 1, message = "cannot_be_less_than_one")
    val priceInCenta: Int,
) {
    @Serializable
    data class IngredientDto(
        @field:NotNull(message = "field_cannot_be_empty")
        val ingredientId: Int?,
        @field:Min(value = 1, message = "cannot_be_less_than_one")
        val quantity: Int,
        @field:NotNull(message = "field_cannot_be_empty")
        val unit: String
    )
}
