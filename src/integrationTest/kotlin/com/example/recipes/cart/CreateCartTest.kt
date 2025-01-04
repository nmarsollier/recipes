package com.example.recipes.cart

import com.example.recipes.RecipesApplication
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertTrue

@SpringBootTest(classes = [RecipesApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integrationTest")
class CreateCartTest {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun `create cart`() {
        val result = webTestClient.post()
            .uri("/carts")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .returnResult()
        val cartId = result.responseBody!!.toInt()
        assertTrue(cartId > 1)
    }
}
