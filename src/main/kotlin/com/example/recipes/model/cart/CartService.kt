package com.example.recipes.model.cart

import com.example.recipes.model.cart.db.Cart
import com.example.recipes.model.cart.db.CartDao
import com.example.recipes.model.cart.db.CartRecipe
import com.example.recipes.model.cart.db.CartRecipeRepository
import com.example.recipes.model.cart.db.CartRepository
import com.example.recipes.model.cart.dtos.CartDto
import com.example.recipes.model.cart.dtos.toCartDto
import com.example.recipes.model.cart.dtos.toCartRecipeDto
import com.example.recipes.model.tools.NotFound
import java.time.LocalDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated

@Service
@Validated
class CartService(
    @Autowired
    private val cartRepository: CartRepository,
    @Autowired
    private val cartRecipeRepository: CartRecipeRepository,
    @Autowired
    private val cartDao: CartDao
) {
    /**
     * Create new Cart
     */
    suspend fun newCart() = cartRepository.save(
        Cart(
            id = null,
            totalInCents = 0,
            enabled = true,
            created = LocalDate.now()
        )
    ).id!!

    /**
     * Gets a cart by its id.
     */
    suspend fun getCart(id: Int): CartDto {
        return cartRepository.findById(id)?.let { cart ->
            val recipes = cartRecipeRepository.findAllByCartId(id).map { it.toCartRecipeDto() }.toList()
            return cart.toCartDto(recipes)
        } ?: throw NotFound
    }

    /**
     * Adds a recipe to a cart.
     */
    @Transactional
    suspend fun addRecipeToCart(
        cartId: Int,
        recipeId: Int
    ) {
        cartRecipeRepository.save(
            CartRecipe(
                cartId = cartId,
                recipeId = recipeId,
            )
        ).id!!

        cartDao.recalculateTotal(cartId)
    }

    /**
     * Removes a recipe from a cart.
     */
    @Transactional
    suspend fun removeRecipeFromCart(
        cartId: Int,
        recipeId: Int
    ) {
        cartRecipeRepository.deleteByCartIdAndRecipeId(cartId, recipeId)
        cartDao.recalculateTotal(cartId)
    }
}
