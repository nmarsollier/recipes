package com.example.recipes.model.ingredient.db

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

/**
 * Represents an ingredient in the database.
 *
 * Just as a reference, not used in the project.
 */
@Table(name = "ingredients")
data class Ingredient(
    @Id
    val id: Int?,
    @Column("name")
    val name: String,
)
