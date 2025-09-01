package com.rentalhub.backend.tenant

import com.rentalhub.backend.landlord.LandlordRepository
import com.rentalhub.backend.tenant.dto.CreateTenantRequest
import com.rentalhub.backend.tenant.dto.TenantResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TenantService(
    private val tenantRepository: TenantRepository,
    private val landlordRepository: LandlordRepository,
    private val passwordEncoder: PasswordEncoder
) {
    private fun currentLandlordEmail(): String =
        SecurityContextHolder.getContext().authentication.name

    fun listTenants(): List<TenantResponse> =
        tenantRepository.findAll()
            .map { TenantResponse(it.id, it.name, it.email) }

    fun getTenant(id: Long): TenantResponse {
        val t = tenantRepository.findById(id)
            .orElseThrow { NoSuchElementException("Tenant not found: $id") }
        return TenantResponse(t.id, t.name, t.email)
    }

    @Transactional
    fun createTenant(req: CreateTenantRequest): TenantResponse {
        // Keep it simple for MVP: ensure unique email at app level
        if (tenantRepository.findByEmail(req.email) != null) {
            throw IllegalArgumentException("Tenant with email already exists")
        }
        val hashed = req.password?.let { passwordEncoder.encode(it) } ?: passwordEncoder.encode("TempPass123!")
        val saved = tenantRepository.save(
            Tenant(
                name = req.name,
                email = req.email,
                password = hashed,
                leases = emptyList()
            )
        )
        // You might send invite email here (future)
        return TenantResponse(saved.id, saved.name, saved.email)
    }
}
