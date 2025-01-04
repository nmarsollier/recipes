package com.example.model.cart

import com.example.recipes.model.cart.CartService
import com.example.recipes.model.cart.db.Cart
import com.example.recipes.model.cart.db.CartDao
import com.example.recipes.model.cart.db.CartRecipe
import com.example.recipes.model.cart.db.CartRecipeRepository
import com.example.recipes.model.cart.db.CartRepository
import com.example.recipes.model.cart.dtos.toCartDto
import com.example.recipes.model.cart.dtos.toCartRecipeDto
import io.mockk.coEvery
import io.mockk.mockk
import io.r2dbc.spi.R2dbcTransientResourceException
import java.time.LocalDate
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class GetCartServiceTest {
    private val cartRepository: CartRepository = mockk()
    private val cartRecipeRepository: CartRecipeRepository = mockk()
    private val cartDao: CartDao = mockk()
    private val cartService = CartService(cartRepository, cartRecipeRepository, cartDao)

    @Test
    fun `test getCart returns cart`() = runBlocking {
        val cartDto = cart.toCartDto(listOf(cartRecipe.toCartRecipeDto()))

        coEvery { cartRepository.findById(1) } returns cart
        coEvery { cartRecipeRepository.findAllByCartId(1) } returns listOf(cartRecipe)
        val result = cartService.getCart(1)

        assertEquals(cartDto, result)
    }

    @Test
    fun `test getCart returns empty result`(): Unit = runBlocking {
        coEvery { cartRepository.findById(1) } returns null

        assertFailsWith<Exception> {
            cartService.getCart(1)
        }
    }

    @Test
    fun `test getCart throws R2dbcTransientResourceException`(): Unit = runBlocking {
        coEvery { cartRepository.findById(1) } coAnswers { throw R2dbcTransientResourceException("Database error") }

        assertFailsWith<R2dbcTransientResourceException> {
            cartService.getCart(1)
        }
    }

    @Test
    fun `test getCart recipes throws R2dbcTransientResourceException`(): Unit = runBlocking {
        coEvery { cartRepository.findById(1) } returns cart
        coEvery { cartRecipeRepository.findAllByCartId(1) } coAnswers { throw R2dbcTransientResourceException("Database error") }

        assertFailsWith<R2dbcTransientResourceException> {
            cartService.getCart(1)
        }
    }
}

private val cart = Cart(
    id = 1,
    totalInCents = 1000,
    enabled = true,
    created = LocalDate.now()
)
private val cartRecipe = CartRecipe(
    id = 1,
    cartId = 1,
    recipeId = 1
)