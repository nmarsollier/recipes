package com.example.recipes.model.cart.db

import java.time.LocalDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

/**
 * Represents a cart in the database.
 */
@Table(name = "cart")
data class Cart(
    @Id
    val id: Int?,
    @Column("total_in_cents")
    val totalInCents: Int,
    @Column("enabled")
    val enabled: Boolean,
    @Column("created")
    val created: LocalDate,
)
