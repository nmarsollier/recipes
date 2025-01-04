package com.example.recipes.recipes

import com.example.recipes.RecipesApplication
import com.example.recipes.model.recipe.dtos.RecipeDto
import com.example.recipes.model.tools.PaginatedResponse
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
class GetRecipesIntegrationTest {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun `get recipes page 0`() {
        val result = webTestClient.get()
            .uri("/recipes?page=0")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .returnResult()
        val body = result.responseBody
        val recipes = body.jsonToObject<PaginatedResponse<RecipeDto>>()
        assertNotNull(recipes)
        assertEquals(0, recipes.page)
        assertEquals(1, recipes.totalPages)
        assertEquals(2, recipes.data.size)
    }

    @Test
    fun `get recipes no page param`() {
        val result = webTestClient.get()
            .uri("/recipes")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .returnResult()
        val body = result.responseBody
        val recipes = body.jsonToObject<PaginatedResponse<RecipeDto>>()
        assertNotNull(recipes)
        assertEquals(0, recipes.page)
        assertEquals(1, recipes.totalPages)
        assertEquals(2, recipes.data.size)
    }

    @Test
    fun `get recipes page 1`() {
        val result = webTestClient.get()
            .uri("/recipes?page=1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .returnResult()
        val body = result.responseBody
        val recipes = body.jsonToObject<PaginatedResponse<RecipeDto>>()
        assertNotNull(recipes)
        assertEquals(1, recipes.page)
        assertEquals(2, recipes.totalPages)
        assertEquals(0, recipes.data.size)
    }
}
