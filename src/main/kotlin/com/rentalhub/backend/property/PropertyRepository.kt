package com.rentalhub.backend.property

import org.springframework.data.jpa.repository.JpaRepository

interface PropertyRepository : JpaRepository<Property, Long> {
    fun findByLandlordId(landlordId: Long): List<Property>
}