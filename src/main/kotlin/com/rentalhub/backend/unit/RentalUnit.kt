package com.rentalhub.backend.unit

import com.rentalhub.backend.lease.Lease
import com.rentalhub.backend.property.Property
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(name = "units")
data class RentalUnit(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val unitNumber: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    val property: Property,

    @OneToMany(mappedBy = "unit", cascade = [CascadeType.ALL], orphanRemoval = true)
    val leases: List<Lease> = emptyList()
)

