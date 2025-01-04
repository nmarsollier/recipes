package com.example.recipes.model.cart.dtos

import com.example.recipes.model.cart.db.Cart
import com.example.recipes.model.cart.db.CartRecipe
import kotlinx.serialization.Serializable

/**
 * Proper representation to expose a cart to clients.
 */
@Serializable
data class CartDto(
    val id: Int,
    val totalInCents: Int,
    val enabled: Boolean,
    val created: String,
    val recipes: List<CartRecipeDto>
) {
    @Serializable
    data class CartRecipeDto(
        val id: Int,
        val recipeId: Int
    )
}

fun Cart.toCartDto(recipes: List<CartDto.CartRecipeDto>) = CartDto(
    id = id!!,
    enabled = enabled,
    created = created.toString(),
    totalInCents = totalInCents,
    recipes = recipes
)

fun CartRecipe.toCartRecipeDto() = CartDto.CartRecipeDto(
    id = id!!,
    recipeId = recipeId
)
