package com.rentalhub.backend.payment.dto

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDate

data class CreatePaymentRequest(
    @field:NotNull val leaseId: Long,
    @field:NotNull @field:DecimalMin("0.01") val amount: BigDecimal,
    // Optional: if omitted, we’ll use today
    val datePaid: LocalDate? = null
)
