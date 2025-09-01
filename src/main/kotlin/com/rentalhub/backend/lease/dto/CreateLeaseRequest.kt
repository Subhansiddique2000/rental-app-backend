package com.rentalhub.backend.lease.dto

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDate

data class CreateLeaseRequest(
    @field:NotNull val tenantId: Long,
    @field:NotNull val unitId: Long,
    @field:NotNull val startDate: LocalDate,
    @field:NotNull @field:Future val endDate: LocalDate,
    @field:NotNull val monthlyRent: BigDecimal
)
