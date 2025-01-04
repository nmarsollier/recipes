package com.example.recipes.model.recipe.db

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("recipe_ingredient")
data class RecipeIngredient(
    @Id
    val id: Int = 0,
    @Column("recipe_id")
    val recipeId: Int,
    @Column("ingredient_id")
    val ingredientId: Int,
    @Column("quantity")
    val quantity: Int,
    @Column("unit")
    val unit: QuantityUnit
)

enum class QuantityUnit {
    GRAMS,
    KILOGRAM,
    LITER,
    MILLILITER,
    CUP,
    TABLESPOON,
    TEASPOON,
    PIECE,
}
