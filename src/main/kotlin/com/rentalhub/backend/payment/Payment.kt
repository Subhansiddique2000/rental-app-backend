package com.rentalhub.backend.payment

import com.rentalhub.backend.lease.Lease
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal
import java.time.LocalDate

@Entity
data class Payment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val amount: BigDecimal,
    val datePaid: LocalDate,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lease_id")
    val lease: Lease
)