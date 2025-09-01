package com.rentalhub.backend.payment

import org.springframework.data.jpa.repository.JpaRepository

interface PaymentRepository : JpaRepository<Payment, Long> {
    // Landlord view: all payments where the lease's unit's property's landlord is X
    fun findAllByLease_Unit_Property_Landlord_Id(landlordId: Long): List<Payment>

    // Tenant view: all payments for a tenant's leases
    fun findAllByLease_Tenant_Id(tenantId: Long): List<Payment>
}
