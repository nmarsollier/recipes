package com.example.model.recipe

import com.example.recipes.model.recipe.RecipesService
import com.example.recipes.model.recipe.db.IngredientsDao
import com.example.recipes.model.recipe.db.QuantityUnit
import com.example.recipes.model.recipe.db.Recipe
import com.example.recipes.model.recipe.db.RecipeIngredient
import com.example.recipes.model.recipe.db.RecipeIngredientRepository
import com.example.recipes.model.recipe.db.RecipeRepository
import com.example.recipes.model.recipe.dtos.NewRecipeDto
import io.mockk.coEvery
import io.mockk.mockk
import io.r2dbc.spi.R2dbcTransientResourceException
import java.time.LocalDate
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class NewRecipeServiceTest {
    private val recipeRepository: RecipeRepository = mockk()
    private val recipeIngredientRepository: RecipeIngredientRepository = mockk()
    private val ingredientsDao: IngredientsDao = mockk()
    private val recipeService = RecipesService(recipeRepository, recipeIngredientRepository, ingredientsDao)

    @Test
    fun `test newRecipe saves recipe and ingredients`() = runBlocking {
        coEvery { recipeRepository.save(recipe) } returns savedRecipe
        coEvery { recipeIngredientRepository.save(recipeIngredient) } returns savedRecipeIngredient
        val result = recipeService.newRecipe(newRecipeDto)

        assertEquals(1, result)
    }

    @Test
    fun `test newRecipe recipeIngredientRepository save thows Exception`(): Unit = runBlocking {
        coEvery { recipeRepository.save(recipe) } returns savedRecipe
        coEvery { recipeIngredientRepository.save(recipeIngredient) } coAnswers {
            throw R2dbcTransientResourceException(
                "Database error"
            )
        }

        assertFailsWith<R2dbcTransientResourceException> {
            recipeService.newRecipe(newRecipeDto)
        }
    }

    @Test
    fun `test newRecipe recipeRepository save thows Exception`(): Unit = runBlocking {
        coEvery { recipeRepository.save(recipe) } coAnswers {
            throw R2dbcTransientResourceException(
                "Database error"
            )
        }

        assertFailsWith<R2dbcTransientResourceException> {
            recipeService.newRecipe(newRecipeDto)
        }
    }
}

private val newRecipeDto = NewRecipeDto(
    title = "New Recipe",
    description = "New Description",
    instructions = "New Instructions",
    minutes = 45,
    difficulty = "Medium",
    vegetarian = false,
    servings = 2,
    priceInCenta = 1500,
    ingredients = listOf(NewRecipeDto.IngredientDto(1, 100, "GRAMS"))
)
private val recipe = Recipe(
    title = newRecipeDto.title,
    description = newRecipeDto.description,
    instructions = newRecipeDto.instructions,
    minutes = newRecipeDto.minutes,
    difficulty = newRecipeDto.difficulty,
    vegetarian = newRecipeDto.vegetarian,
    servings = newRecipeDto.servings,
    created = LocalDate.now(),
    priceInCents = newRecipeDto.priceInCenta
)
private val savedRecipe = recipe.copy(id = 1)
private val recipeIngredient = RecipeIngredient(
    recipeId = 1,
    ingredientId = 1,
    quantity = 100,
    unit = QuantityUnit.GRAMS
)
private val savedRecipeIngredient = recipeIngredient.copy(id = 1)
