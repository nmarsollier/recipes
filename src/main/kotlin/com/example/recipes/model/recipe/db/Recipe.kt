package com.example.recipes.model.recipe.db

import java.time.LocalDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("recipes")
data class Recipe(
    @Id
    val id: Int? = null,
    @Column("title")
    val title: String,
    @Column("description")
    val description: String,
    @Column("instructions")
    val instructions: String,
    @Column("minutes")
    val minutes: Int,
    @Column("difficulty")
    val difficulty: String,
    @Column("vegetarian")
    val vegetarian: Boolean,
    @Column("servings")
    val servings: Int,
    @Column("created")
    val created: LocalDate,
    @Column("price_in_cents")
    val priceInCents: Int,
)