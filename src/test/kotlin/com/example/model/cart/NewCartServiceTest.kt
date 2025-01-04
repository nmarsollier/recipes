package com.example.model.cart

import com.example.recipes.model.cart.CartService
import com.example.recipes.model.cart.db.Cart
import com.example.recipes.model.cart.db.CartRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.r2dbc.spi.R2dbcTransientResourceException
import java.time.LocalDate
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class NewCartServiceTest {
    private val cartRepository: CartRepository = mockk()
    private val cartService = CartService(cartRepository, mockk(), mockk())

    @Test
    fun `test newCart saves cart`() = runBlocking {
        coEvery { cartRepository.save(cart) } returns savedCart
        val result = cartService.newCart()

        assertEquals(1, result)
    }

    @Test
    fun `test newCart cartRepository save throws Exception`(): Unit = runBlocking {
        coEvery { cartRepository.save(cart) } coAnswers {
            throw R2dbcTransientResourceException("Database error")
        }

        assertFailsWith<R2dbcTransientResourceException> {
            cartService.newCart()
        }
    }
}

private val cart = Cart(
    id = null,
    totalInCents = 0,
    enabled = true,
    created = LocalDate.now()
)
private val savedCart = cart.copy(id = 1)