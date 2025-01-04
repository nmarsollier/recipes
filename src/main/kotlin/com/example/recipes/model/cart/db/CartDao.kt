package com.example.recipes.model.cart.db

import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
class CartDao(
    private val databaseClient: DatabaseClient
) {
    suspend fun recalculateTotal(id: Int): Unit {
        databaseClient.sql(
            """
            UPDATE cart 
            SET total_in_cents = COALESCE((
              SELECT SUM(recipes.price_in_cents) 
                    FROM cart_recipe 
                    JOIN recipes ON cart_recipe.recipe_id = recipes.id 
                    WHERE cart_recipe.cart_id = cart.id
              ), 0)
            WHERE cart.id = :id
            """.trimIndent()
        ).bind("id", id).fetch().rowsUpdated().awaitFirst()
    }
}
