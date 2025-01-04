package com.example.model.cart

import com.example.recipes.model.cart.CartService
import com.example.recipes.model.cart.db.CartDao
import com.example.recipes.model.cart.db.CartRecipe
import com.example.recipes.model.cart.db.CartRecipeRepository
import com.example.recipes.model.cart.db.CartRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.r2dbc.spi.R2dbcTransientResourceException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class AddRecipeToCartServiceTest {
    private val cartRepository: CartRepository = mockk()
    private val cartRecipeRepository: CartRecipeRepository = mockk()
    private val cartDao: CartDao = mockk()
    private val cartService = CartService(cartRepository, cartRecipeRepository, cartDao)

    @Test
    fun `test addRecipeToCart saves recipe to cart`() = runBlocking {
        coEvery { cartRecipeRepository.save(cartRecipe) } returns cartRecipeResponse
        coEvery { cartDao.recalculateTotal(1) } returns Unit

        cartService.addRecipeToCart(1, 1)

        coVerify { cartRecipeRepository.save(cartRecipe) }
        coVerify { cartDao.recalculateTotal(1) }
    }

    @Test
    fun `test addRecipeToCart cartRecipeRepository save throws Exception`(): Unit = runBlocking {
        coEvery { cartRecipeRepository.save(cartRecipe) } coAnswers {
            throw R2dbcTransientResourceException("Database error")
        }

        assertFailsWith<R2dbcTransientResourceException> {
            cartService.addRecipeToCart(1, 1)
        }
    }

    @Test
    fun `test addRecipeToCart cartDao recalculateTotal throws Exception`(): Unit = runBlocking {
        coEvery { cartRecipeRepository.save(cartRecipe) } returns cartRecipeResponse
        coEvery { cartDao.recalculateTotal(1) } coAnswers {
            throw R2dbcTransientResourceException("Database error")
        }

        assertFailsWith<R2dbcTransientResourceException> {
            cartService.addRecipeToCart(1, 1)
        }
    }
}

private val cartRecipe = CartRecipe(
    cartId = 1,
    recipeId = 1
)
private val cartRecipeResponse = cartRecipe.copy(
    id = 1,
)
