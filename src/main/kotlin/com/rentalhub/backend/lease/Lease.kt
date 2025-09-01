package com.rentalhub.backend.lease

import com.rentalhub.backend.payment.Payment
import com.rentalhub.backend.tenant.Tenant
import com.rentalhub.backend.unit.RentalUnit
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import java.math.BigDecimal
import java.time.LocalDate

@Entity
data class Lease(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val startDate: LocalDate,
    val endDate: LocalDate,
    val monthlyRent: BigDecimal,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    val unit: RentalUnit,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    val tenant: Tenant,

    @OneToMany(mappedBy = "lease", cascade = [CascadeType.ALL], orphanRemoval = true)
    val payments: List<Payment> = emptyList()
)