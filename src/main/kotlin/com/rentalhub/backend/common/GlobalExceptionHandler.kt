package com.rentalhub.backend.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class, BindException::class)
    fun handleValidation(ex: Exception): ResponseEntity<Map<String, Any>> {
        val errors = when (ex) {
            is MethodArgumentNotValidException -> ex.bindingResult.fieldErrors.associate { it.field to (it.defaultMessage ?: "Invalid") }
            is BindException -> ex.bindingResult.fieldErrors.associate { it.field to (it.defaultMessage ?: "Invalid") }
            else -> emptyMap()
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(mapOf("error" to "validation_failed", "details" to errors))
    }

    @ExceptionHandler(NoSuchElementException::class, IllegalStateException::class, IllegalArgumentException::class)
    fun handleNotFoundOrState(ex: RuntimeException): ResponseEntity<Map<String, String>> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to ex.message.orEmpty()))
}
