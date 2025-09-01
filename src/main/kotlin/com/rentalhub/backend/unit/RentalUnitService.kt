package com.rentalhub.backend.unit

import com.rentalhub.backend.landlord.LandlordRepository
import com.rentalhub.backend.property.PropertyRepository
import com.rentalhub.backend.unit.dto.CreateUnitRequest
import com.rentalhub.backend.unit.dto.UnitResponse
import com.rentalhub.backend.unit.dto.UpdateUnitRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RentalUnitService(
    private val unitRepository: RentalUnitRepository,
    private val propertyRepository: PropertyRepository,
    private val landlordRepository: LandlordRepository
) {
    private fun currentLandlordId(): Long {
        val email = SecurityContextHolder.getContext().authentication.name
        return landlordRepository.findByEmail(email)?.id
            ?: throw IllegalStateException("Authenticated landlord not found")
    }

    private fun loadMyProperty(propertyId: Long) =
        propertyRepository.findById(propertyId).orElseThrow {
            NoSuchElementException("Property not found: $propertyId")
        }.also { property ->
            require(property.landlord.id == currentLandlordId()) {
                "Forbidden: property does not belong to you"
            }
        }

    fun listUnits(propertyId: Long): List<UnitResponse> {
        loadMyProperty(propertyId)
        return unitRepository.findByPropertyId(propertyId)
            .map { UnitResponse(it.id, it.unitNumber) }
    }

    @Transactional
    fun create(propertyId: Long, req: CreateUnitRequest): UnitResponse {
        val property = loadMyProperty(propertyId)
        val saved = unitRepository.save(RentalUnit(unitNumber = req.unitNumber, property = property))
        return UnitResponse(saved.id, saved.unitNumber)
    }

    @Transactional
    fun update(unitId: Long, req: UpdateUnitRequest): UnitResponse {
        val myId = currentLandlordId()
        val entity = unitRepository.findById(unitId)
            .orElseThrow { NoSuchElementException("Unit not found: $unitId") }
        require(entity.property.landlord.id == myId) { "Forbidden: unit does not belong to you" }

        val updated = unitRepository.save(entity.copy(unitNumber = req.unitNumber))
        return UnitResponse(updated.id, updated.unitNumber)
    }

    @Transactional
    fun delete(unitId: Long) {
        val myId = currentLandlordId()
        val entity = unitRepository.findById(unitId)
            .orElseThrow { NoSuchElementException("Unit not found: $unitId") }
        require(entity.property.landlord.id == myId) { "Forbidden: unit does not belong to you" }
        unitRepository.delete(entity)
    }
}
