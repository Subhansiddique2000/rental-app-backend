package com.rentalhub.backend.tenant


import com.rentalhub.backend.lease.Lease
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
data class Tenant(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,
    val email: String,
    val password: String,

    @OneToMany(mappedBy = "tenant", cascade = [CascadeType.ALL], orphanRemoval = true)
    val leases: List<Lease> = emptyList()
)
