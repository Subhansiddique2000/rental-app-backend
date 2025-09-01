package com.rentalhub.backend.tenant

import org.springframework.data.jpa.repository.JpaRepository

interface TenantRepository : JpaRepository<Tenant, Long> {
    fun findByEmail(email: String): Tenant?
}