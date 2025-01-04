package com.example.recipes.model.recipe.db

import com.example.recipes.model.recipe.dtos.IngredientDto
import com.example.recipes.model.tools.toIntOrNull
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

/**
 * Convenient Dao to access the ingredient's info.
 */
@Service
@Validated
class IngredientsDao(
    private val databaseClient: DatabaseClient
) {
    suspend fun getIngredients(recipeId: Int): List<IngredientDto> {
        return databaseClient.sql(
            """
              SELECT ingredients.id, ingredients.name, recipe_ingredient.quantity, recipe_ingredient.unit 
              FROM recipe_ingredient 
              INNER JOIN ingredients 
                ON recipe_ingredient.ingredient_id = ingredients.id
              WHERE recipe_ingredient.recipe_id = :recipeId
            """.trimIndent()
        )
            .bind("recipeId", recipeId)
            .map { row, _ ->
                IngredientDto(
                    id = row.get("id", Any::class.java).toIntOrNull()!!,
                    name = row.get("name", String::class.java)!!,
                    quantity = row.get("quantity", Any::class.java).toIntOrNull()!!,
                    unit = QuantityUnit.valueOf(row.get("unit", String::class.java)!!)
                )
            }
            .all()
            .collectList()
            .awaitFirst()
    }
}
