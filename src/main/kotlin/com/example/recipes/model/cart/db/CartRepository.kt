package com.example.recipes.model.cart.db

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

/**
 * Generic repository to access the cart table.
 */
@Repository
interface CartRepository : CoroutineCrudRepository<Cart, Int>

