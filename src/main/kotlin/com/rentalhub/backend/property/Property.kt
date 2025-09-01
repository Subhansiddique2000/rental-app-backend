package com.rentalhub.backend.property

import com.rentalhub.backend.landlord.Landlord
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

@Entity
data class Property(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,
    val address: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landlord_id")
    val landlord: Landlord,

    @OneToMany(mappedBy = "property", cascade = [CascadeType.ALL], orphanRemoval = true)
    val units: List<RentalUnit> = emptyList()

)
