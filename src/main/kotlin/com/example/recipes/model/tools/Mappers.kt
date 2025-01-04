package com.example.recipes.model.tools

/**
 * Extension function to convert any object to an integer or null if it's not possible.
 * Used by integration tests
 */
fun Any?.toIntOrNull(): Int? {
    return when (this) {
        is Int -> this
        is Integer -> this.toInt()
        else -> null
    }
}