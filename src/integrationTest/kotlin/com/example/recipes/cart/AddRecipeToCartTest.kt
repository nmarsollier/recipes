package com.example.recipes.cart

import com.example.recipes.RecipesApplication
import com.example.recipes.model.cart.dtos.CartDto
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest(classes = [RecipesApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integrationTest")
class AddRecipeToCartTest {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun `add recipe to cart`() {
        val newCart = webTestClient.post()
            .uri("/carts")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .returnResult()
        val cartId = newCart.responseBody

        webTestClient.post()
            .uri("/carts/${cartId}/add_recipe?recipeId=1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .returnResult()

        webTestClient.post()
            .uri("/carts/${cartId}/add_recipe?recipeId=2")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .returnResult()

        val result = webTestClient.get()
            .uri("/carts/${cartId}")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(CartDto::class.java)
            .returnResult()
        val cart = result.responseBody
        assertNotNull(cart)
        assertEquals(7500, cart.totalInCents)
        assertEquals(2, cart.recipes.size)
    }

    @Test
    fun `add invalid recipe to cart`() {
        val result = webTestClient.post()
            .uri("/carts/1/add_recipe?recipeId=100")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
        val body = result.responseBody
        assertEquals(body, "Invalid request")
    }

    @Test
    fun `add recipe to invalid cart`() {
        val result = webTestClient.post()
            .uri("/carts/100/add_recipe?recipeId=1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
        val body = result.responseBody
        assertEquals(body, "Invalid request")
    }
}
