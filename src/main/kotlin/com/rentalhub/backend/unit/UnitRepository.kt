package com.rentalhub.backend.unit

import org.springframework.data.jpa.repository.JpaRepository

interface UnitRepository : JpaRepository<Unit, Long> {
    fun findByPropertyId(propertyId: Long): List<Unit>
}