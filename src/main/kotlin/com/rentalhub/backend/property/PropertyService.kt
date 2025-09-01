package com.rentalhub.backend.property

import com.rentalhub.backend.landlord.LandlordRepository
import com.rentalhub.backend.property.dto.CreatePropertyRequest
import com.rentalhub.backend.property.dto.PropertyResponse
import com.rentalhub.backend.property.dto.UpdatePropertyRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PropertyService(
    private val propertyRepository: PropertyRepository,
    private val landlordRepository: LandlordRepository
) {
    private fun currentLandlordId(): Long {
        val email = SecurityContextHolder.getContext().authentication.name
        return landlordRepository.findByEmail(email)?.id
            ?: throw IllegalStateException("Authenticated landlord not found")
    }

    fun listMyProperties(): List<PropertyResponse> =
        propertyRepository.findByLandlordId(currentLandlordId())
            .map { PropertyResponse(it.id, it.name, it.address) }

    @Transactional
    fun create(req: CreatePropertyRequest): PropertyResponse {
        val landlord = landlordRepository.findById(currentLandlordId())
            .orElseThrow { IllegalStateException("Landlord not found") }
        val saved = propertyRepository.save(
            Property(name = req.name, address = req.address, landlord = landlord)
        )
        return PropertyResponse(saved.id, saved.name, saved.address)
    }

    @Transactional
    fun update(id: Long, req: UpdatePropertyRequest): PropertyResponse {
        val myId = currentLandlordId()
        val entity = propertyRepository.findById(id)
            .orElseThrow { NoSuchElementException("Property not found") }
        require(entity.landlord.id == myId) { "Cannot modify another landlord's property" }

        val updated = propertyRepository.save(
            entity.copy(name = req.name, address = req.address)
        )
        return PropertyResponse(updated.id, updated.name, updated.address)
    }

    @Transactional
    fun delete(id: Long) {
        val myId = currentLandlordId()
        val entity = propertyRepository.findById(id)
            .orElseThrow { NoSuchElementException("Property not found") }
        require(entity.landlord.id == myId) { "Cannot delete another landlord's property" }
        propertyRepository.delete(entity)
    }
}
