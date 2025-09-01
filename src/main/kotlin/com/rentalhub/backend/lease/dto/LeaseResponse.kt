package com.rentalhub.backend.lease.dto

import java.math.BigDecimal
import java.time.LocalDate

data class LeaseResponse(
    val id: Long,
    val tenantId: Long,
    val unitId: Long,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val monthlyRent: BigDecimal
)
