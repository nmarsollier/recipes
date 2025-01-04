package com.example.recipes.rest

import com.example.recipes.model.recipe.RecipesService
import com.example.recipes.model.recipe.dtos.NewRecipeDto
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RecipeController(
    @Autowired
    private val recipeService: RecipesService
) {
    @Operation(summary = "Get available recipes")
    @GetMapping("/recipes")
    suspend fun getRecipes(
        @RequestParam("page")
        page: Int = 0
    ) = recipeService.getRecipes(page)

    @PostMapping("/recipes")
    @Operation(summary = "Create a recipe")
    suspend fun postRecipes(
        @RequestBody
        recipe: NewRecipeDto
    ) = recipeService.newRecipe(recipe)
}
