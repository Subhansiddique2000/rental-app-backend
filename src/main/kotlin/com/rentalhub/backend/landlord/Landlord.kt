package com.rentalhub.backend.landlord

import com.rentalhub.backend.property.Property
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
data class Landlord(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,
    val email: String,
    val password: String,

    @OneToMany(mappedBy = "landlord", cascade = [CascadeType.ALL], orphanRemoval = true)
    val properties: List<Property> = emptyList()
)
