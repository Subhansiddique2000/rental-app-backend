package com.rentalhub.backend.lease

import com.rentalhub.backend.landlord.LandlordRepository
import com.rentalhub.backend.lease.dto.CreateLeaseRequest
import com.rentalhub.backend.lease.dto.LeaseResponse
import com.rentalhub.backend.tenant.TenantRepository
import com.rentalhub.backend.unit.RentalUnitRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.rentalhub.backend.unit.RentalUnit as RentalUnit   // 👈 alias

@Service
class LeaseService(
    private val leaseRepository: LeaseRepository,
    private val unitRepository: RentalUnitRepository,
    private val tenantRepository: TenantRepository,
    private val landlordRepository: LandlordRepository
) {
    private fun currentLandlordId(): Long {
        val email = SecurityContextHolder.getContext().authentication.name
        return landlordRepository.findByEmail(email)?.id
            ?: throw IllegalStateException("Authenticated landlord not found")
    }

    fun listLeasesForLandlord(): List<LeaseResponse> {
        val myId = currentLandlordId()
        return leaseRepository.findAll()
            .filter { it.unit.property.landlord.id == myId }
            .map { LeaseResponse(it.id, it.tenant.id, it.unit.id, it.startDate, it.endDate, it.monthlyRent) }
    }

    @Transactional
    fun create(req: CreateLeaseRequest): LeaseResponse {
        val myId = currentLandlordId()

        val unitEntity: RentalUnit = unitRepository.findById(req.unitId)
            .orElseThrow { NoSuchElementException("Unit not found: ${req.unitId}") }
        require(unitEntity.property.landlord.id == myId) { "Forbidden: unit not under your property" }

        val tenant = tenantRepository.findById(req.tenantId)
            .orElseThrow { NoSuchElementException("Tenant not found: ${req.tenantId}") }

        val saved = leaseRepository.save(
            Lease(
                startDate = req.startDate,
                endDate = req.endDate,
                monthlyRent = req.monthlyRent,
                unit = unitEntity,       // 👈 clear now
                tenant = tenant,
                payments = emptyList()
            )
        )
        return LeaseResponse(saved.id, tenant.id, unitEntity.id, saved.startDate, saved.endDate, saved.monthlyRent)
    }

    fun listMyLeasesForTenant(tenantEmail: String): List<LeaseResponse> {
        val t = tenantRepository.findByEmail(tenantEmail)
            ?: throw NoSuchElementException("Tenant not found")
        return leaseRepository.findAll()
            .filter { it.tenant.id == t.id }
            .map { LeaseResponse(it.id, t.id, it.unit.id, it.startDate, it.endDate, it.monthlyRent) }
    }
}
