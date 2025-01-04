package com.example.recipes.rest.tools

import com.example.recipes.model.tools.AlreadyExist
import com.example.recipes.model.tools.NotFound
import jakarta.validation.ConstraintViolationException
import kotlinx.serialization.Serializable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(ex: ConstraintViolationException): ResponseEntity<DataValidationError> {
        return ResponseEntity(
            DataValidationError(
                ex.constraintViolations.associate {
                    it.propertyPath.toString() to it.message
                }), HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(org.springframework.web.server.ServerWebInputException::class)
    fun handleInvalidBody(ex: Exception): ResponseEntity<String> {
        return ResponseEntity(
            ex.cause?.message ?: ex.message ?: "Invalid request",
            HttpStatus.BAD_REQUEST,
        )
    }

    @ExceptionHandler(org.springframework.dao.DuplicateKeyException::class)
    fun alreadyExist(ex: Exception): ResponseEntity<String> {
        return ResponseEntity(
            "Document already exist",
            HttpStatus.BAD_REQUEST,
        )
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException::class)
    fun invalidId(ex: Exception): ResponseEntity<String> {
        val message = when {
            ex.message?.contains("recipe_id_fkey") == true -> "Invalid recipe id"
            ex.message?.contains("ingredient_id_fkey") == true -> "Invalid ingredient id"
            ex.message?.contains("cart_id_fkey") == true -> "Invalid cart id"
            else -> "Invalid request"
        }

        return ResponseEntity(
            message,
            HttpStatus.BAD_REQUEST,
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception): ResponseEntity<String> {
        ex.printStackTrace()

        val code = when (ex) {
            NotFound -> HttpStatus.BAD_REQUEST
            AlreadyExist -> HttpStatus.BAD_REQUEST
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
        return ResponseEntity(
            "${ex.message}", code
        )
    }
}

@Serializable
data class DataValidationError(
    val errors: Map<String, String>
)
