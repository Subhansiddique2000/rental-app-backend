package com.rentalhub.backend.unit.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateUnitRequest(
    @field:NotBlank @field:Size(max = 32)
    val unitNumber: String
)
