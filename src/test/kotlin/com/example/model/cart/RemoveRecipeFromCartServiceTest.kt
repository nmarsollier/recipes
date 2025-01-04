package com.example.model.cart

import com.example.recipes.model.cart.CartService
import com.example.recipes.model.cart.db.CartDao
import com.example.recipes.model.cart.db.CartRecipeRepository
import com.example.recipes.model.cart.db.CartRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.r2dbc.spi.R2dbcTransientResourceException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class RemoveRecipeFromCartServiceTest {
    private val cartRepository: CartRepository = mockk()
    private val cartRecipeRepository: CartRecipeRepository = mockk()
    private val cartDao: CartDao = mockk()
    private val cartService = CartService(cartRepository, cartRecipeRepository, cartDao)

    @Test
    fun `test removeRecipeFromCart removes recipe from cart`() = runBlocking {
        coEvery { cartRecipeRepository.deleteByCartIdAndRecipeId(1, 1) } returns 1
        coEvery { cartDao.recalculateTotal(1) } returns Unit

        cartService.removeRecipeFromCart(1, 1)

        coVerify { cartRecipeRepository.deleteByCartIdAndRecipeId(1, 1) }
        coVerify { cartDao.recalculateTotal(1) }
    }

    @Test
    fun `test removeRecipeFromCart cartRecipeRepository delete throws Exception`(): Unit = runBlocking {
        coEvery { cartRecipeRepository.deleteByCartIdAndRecipeId(1, 1) } coAnswers {
            throw R2dbcTransientResourceException("Database error")
        }

        assertFailsWith<R2dbcTransientResourceException> {
            cartService.removeRecipeFromCart(1, 1)
        }
    }

    @Test
    fun `test removeRecipeFromCart cartDao recalculateTotal throws Exception`(): Unit = runBlocking {
        coEvery { cartRecipeRepository.deleteByCartIdAndRecipeId(1, 1) } returns 1
        coEvery { cartDao.recalculateTotal(1) } coAnswers {
            throw R2dbcTransientResourceException("Database error")
        }

        assertFailsWith<R2dbcTransientResourceException> {
            cartService.removeRecipeFromCart(1, 1)
        }
    }
}