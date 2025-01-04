package com.example.recipes.model.recipe.dtos

import com.example.recipes.model.recipe.db.Recipe
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(
    val id: Int?,
    val title: String,
    val description: String,
    val instructions: String,
    val minutes: Int,
    val difficulty: String,
    val vegetarian: Boolean,
    val servings: Int,
    val priceInCents: Int,
    val ingredients: List<IngredientDto> = emptyList()
)

fun Recipe.toRecipeDto(ingredients: List<IngredientDto>?) = RecipeDto(
    id = id,
    title = title,
    description = description,
    instructions = instructions,
    minutes = minutes,
    difficulty = difficulty,
    vegetarian = vegetarian,
    servings = servings,
    priceInCents = priceInCents,
    ingredients = ingredients ?: emptyList()
)