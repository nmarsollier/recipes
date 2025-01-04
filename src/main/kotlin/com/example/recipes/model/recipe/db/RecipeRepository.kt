package com.example.recipes.model.recipe.db

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

/**
 * Generic repository to access the recipe_ingredient table.
 */
@Repository
interface RecipeRepository : CoroutineCrudRepository<Recipe, Int>
