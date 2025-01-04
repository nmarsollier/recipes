package com.example.model.recipe

import com.example.recipes.model.recipe.RecipesService
import com.example.recipes.model.recipe.db.IngredientsDao
import com.example.recipes.model.recipe.db.QuantityUnit
import com.example.recipes.model.recipe.db.Recipe
import com.example.recipes.model.recipe.db.RecipeIngredientRepository
import com.example.recipes.model.recipe.db.RecipeRepository
import com.example.recipes.model.recipe.dtos.IngredientDto
import com.example.recipes.model.recipe.dtos.RecipeDto
import com.example.recipes.model.recipe.dtos.toRecipeDto
import com.example.recipes.model.tools.PaginatedResponse
import io.mockk.coEvery
import io.mockk.mockk
import io.r2dbc.spi.R2dbcTransientResourceException
import java.time.LocalDate
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class GetRecipesServiceTest {
    private val recipeRepository: RecipeRepository = mockk()
    private val recipeIngredientRepository: RecipeIngredientRepository = mockk()
    private val ingredientsDao: IngredientsDao = mockk()
    private val recipeService = RecipesService(recipeRepository, recipeIngredientRepository, ingredientsDao)

    @Test
    fun `test getRecipes returns paginated recipes`() = runBlocking {
        val recipeDto = recipe.toRecipeDto(listOf(ingredientDto))

        coEvery { recipeRepository.findAll() } returns flowOf(recipe)
        coEvery { ingredientsDao.getIngredients(1) } returns listOf(ingredientDto)
        val result = recipeService.getRecipes(0)

        assertEquals(PaginatedResponse(listOf(recipeDto), 0, 1), result)
    }

    @Test
    fun `test getRecipes returns multiple paginated recipes`() = runBlocking {
        val recipes = Array(10) { i -> recipe.copy(id = i) }

        coEvery { recipeRepository.findAll() } returns flowOf(*recipes)
        coEvery { recipeRepository.count() } returns 100
        coEvery { ingredientsDao.getIngredients(any()) } returns listOf(ingredientDto)
        val result = recipeService.getRecipes(0)

        assertEquals(11, result.totalPages)
        assertEquals(10, result.data.size)
    }

    @Test
    fun `test getRecipes returns empty result`() = runBlocking {
        coEvery { recipeRepository.findAll() } returns emptyFlow()
        val result = recipeService.getRecipes(0)

        assertEquals(PaginatedResponse<RecipeDto>(emptyList(), 0, 1), result)
    }

    @Test
    fun `test getRecipes throws R2dbcTransientResourceException`(): Unit = runBlocking {
        coEvery { recipeRepository.findAll() } coAnswers { throw R2dbcTransientResourceException("Database error") }
        assertFailsWith<R2dbcTransientResourceException> {
            recipeService.getRecipes(0)
        }
    }

    @Test
    fun `test getIngredients returns empty result throws R2dbcTransientResourceException`(): Unit = runBlocking {
        coEvery { recipeRepository.findAll() } returns flowOf(recipe)
        coEvery { ingredientsDao.getIngredients(1) } coAnswers { throw R2dbcTransientResourceException("Database error") }

        assertFailsWith<R2dbcTransientResourceException> {
            recipeService.getRecipes(0)
        }
    }
}

private val recipe = Recipe(
    id = 1,
    title = "Test Recipe",
    description = "Test Description",
    instructions = "Test Instructions",
    minutes = 30,
    difficulty = "Easy",
    vegetarian = true,
    servings = 4,
    created = LocalDate.now(),
    priceInCents = 1000
)
private val ingredientDto = IngredientDto(1, "ingredient1", 1, QuantityUnit.GRAMS)
