package com.example.recipes.model.recipe

import com.example.recipes.model.recipe.db.IngredientsDao
import com.example.recipes.model.recipe.db.QuantityUnit
import com.example.recipes.model.recipe.db.Recipe
import com.example.recipes.model.recipe.db.RecipeIngredient
import com.example.recipes.model.recipe.db.RecipeIngredientRepository
import com.example.recipes.model.recipe.db.RecipeRepository
import com.example.recipes.model.recipe.dtos.NewRecipeDto
import com.example.recipes.model.recipe.dtos.RecipeDto
import com.example.recipes.model.recipe.dtos.toRecipeDto
import com.example.recipes.model.tools.PaginatedResponse
import jakarta.validation.Valid
import java.time.LocalDate
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated

const val PAGE_SIZE = 10

@Service
@Validated
class RecipesService(
    @Autowired
    private val recipeRepository: RecipeRepository,
    @Autowired
    private val recipeIngredientRepository: RecipeIngredientRepository,
    @Autowired
    private val ingredientsDao: IngredientsDao,
) {
    suspend fun getRecipes(page: Int): PaginatedResponse<RecipeDto> {
        val data = recipeRepository.findAll()
            .drop(page * PAGE_SIZE)
            .take(PAGE_SIZE)
            .map { recipe ->
                recipe.toRecipeDto(ingredientsDao.getIngredients(recipe.id!!))
            }.toList()
        val totalPages = if (data.size == PAGE_SIZE) {
            (recipeRepository.count() / PAGE_SIZE)
        } else {
            page
        }

        return PaginatedResponse(
            data = data,
            page = page,
            totalPages = totalPages.toInt()+1
        )
    }

    @Transactional
    suspend fun newRecipe(
        @Valid
        recipe: NewRecipeDto
    ): Int {
        val r = recipeRepository.save(
            Recipe(
                title = recipe.title,
                description = recipe.description,
                instructions = recipe.instructions,
                minutes = recipe.minutes,
                difficulty = recipe.difficulty,
                vegetarian = recipe.vegetarian,
                servings = recipe.servings,
                created = LocalDate.now(),
                priceInCents = recipe.priceInCenta
            )
        )

        recipe.ingredients.forEach {
            recipeIngredientRepository.save(
                RecipeIngredient(
                    recipeId = r.id!!,
                    ingredientId = it.ingredientId!!,
                    quantity = it.quantity,
                    unit = QuantityUnit.valueOf(it.unit)
                )
            )
        }

        return r.id!!
    }
}

