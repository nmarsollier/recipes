package com.example.recipes.tools

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
}

inline fun <reified T> String?.jsonToObject(): T? {
    if (this == null) {
        return null
    }
    return try {
        json.decodeFromString<T>(this)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

inline fun <reified T> T?.toJson(): String? {
    this ?: return null
    return json.encodeToString(this)
}