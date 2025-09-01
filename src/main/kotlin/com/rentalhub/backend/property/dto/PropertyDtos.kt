package com.rentalhub.backend.property.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreatePropertyRequest(
    @field:NotBlank @field:Size(max = 120) val name: String,
    @field:NotBlank @field:Size(max = 240) val address: String
)

data class UpdatePropertyRequest(
    @field:NotBlank @field:Size(max = 120) val name: String,
    @field:NotBlank @field:Size(max = 240) val address: String
)

data class PropertyResponse(
    val id: Long,
    val name: String,
    val address: String
)
