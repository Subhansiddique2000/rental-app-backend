package com.rentalhub.backend.unit

import org.springframework.data.jpa.repository.JpaRepository

interface RentalUnitRepository : JpaRepository<RentalUnit, Long> {
    fun findByPropertyId(propertyId: Long): List<RentalUnit>
}

