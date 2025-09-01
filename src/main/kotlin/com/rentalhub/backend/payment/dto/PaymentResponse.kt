package com.rentalhub.backend.payment.dto

import java.math.BigDecimal
import java.time.LocalDate

data class PaymentResponse(
    val id: Long,
    val leaseId: Long,
    val amount: BigDecimal,
    val datePaid: LocalDate
)
