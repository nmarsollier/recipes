package com.example.recipes.rest

import com.example.recipes.model.cart.CartService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.constraints.NotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
class CartController(
    @Autowired
    private val cartService: CartService
) {
    @PostMapping("/carts")
    @Operation(summary = "Create cart")
    suspend fun postCarts() = cartService.newCart()

    @GetMapping("/carts/{cartId}")
    @Operation(summary = "Get Cart by Id")
    suspend fun getCartsId(
        @PathVariable("cartId")
        cartId: Int,
    ) = cartService.getCart(cartId)

    @PostMapping("/carts/{cartId}/add_recipe")
    @Operation(summary = "Add recipe to Cart")
    suspend fun postCartsIdAddRecipe(
        @PathVariable("cartId")
        cartId: Int,
        @RequestParam("recipeId")
        recipeId: Int
    ) = cartService.addRecipeToCart(cartId, recipeId)

    @DeleteMapping("/carts/{cartId}/recipes/{recipeId}")
    @Operation(summary = "Remove Recipe from Cart")
    suspend fun deleteCartsIdRecipesId(
        @PathVariable("cartId")
        cartId: Int,
        @PathVariable("recipeId")
        recipeId: Int,
    ) = cartService.removeRecipeFromCart(cartId, recipeId)
}

data class AddRecipeDto(
    @NotNull
    val recipeId: Int
)
