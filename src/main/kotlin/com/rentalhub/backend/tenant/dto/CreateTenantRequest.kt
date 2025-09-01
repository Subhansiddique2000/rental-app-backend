package com.rentalhub.backend.tenant.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateTenantRequest(
    @field:NotBlank @field:Size(max = 80) val name: String,
    @field:NotBlank @field:Email val email: String,
    // MVP: optional password for now; we'll wire tenant auth next
    @field:Size(max = 120) val password: String? = null
)
