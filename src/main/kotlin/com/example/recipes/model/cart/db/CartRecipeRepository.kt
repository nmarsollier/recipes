package com.example.recipes.model.cart.db

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

/**
 * Generic repository to access the cart_recipe table.
 */
@Repository
interface CartRecipeRepository : CoroutineCrudRepository<CartRecipe, Int> {
    suspend fun findAllByCartId(cartId: Int): List<CartRecipe>

    suspend fun deleteByCartIdAndRecipeId(
        cartId: Int,
        recipeId: Int
    ): Int
}
