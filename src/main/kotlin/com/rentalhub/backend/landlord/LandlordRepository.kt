package com.rentalhub.backend.landlord

import org.springframework.data.jpa.repository.JpaRepository


interface LandlordRepository : JpaRepository<Landlord, Long> {
    fun findByEmail(email: String): Landlord?
}