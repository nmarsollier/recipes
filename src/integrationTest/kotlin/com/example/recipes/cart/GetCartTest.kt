package com.example.recipes.cart

import com.example.recipes.RecipesApplication
import com.example.recipes.model.cart.dtos.CartDto
import com.example.recipes.tools.jsonToObject
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
class GetCartTest {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun `get cart 1`() {
        val result = webTestClient.get()
            .uri("/carts/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .returnResult()
        val body = result.responseBody
        val cart = body.jsonToObject<CartDto>()
        assertNotNull(cart)
        assertEquals(0, cart.totalInCents)
        assertEquals(0, cart.recipes.size)
    }

    @Test
    fun `get cart 200 - Not found`() {
        val result = webTestClient.get()
            .uri("/carts/200")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
        val body = result.responseBody
        assertEquals(body, "Document not found")
    }
}
