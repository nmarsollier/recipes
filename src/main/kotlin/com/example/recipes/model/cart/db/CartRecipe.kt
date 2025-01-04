package com.example.recipes.model.cart.db

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

/**
 * Represents the relationship between cart and recipe.
 */
@Table(name = "cart_recipe")
data class CartRecipe(
    @Id
    val id: Int? = null,
    @Column("cart_id")
    val cartId: Int,
    @Column("recipe_id")
    val recipeId: Int,
)
