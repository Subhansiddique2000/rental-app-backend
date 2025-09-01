package com.rentalhub.backend.payment

import com.rentalhub.backend.landlord.LandlordRepository
import com.rentalhub.backend.lease.LeaseRepository
import com.rentalhub.backend.payment.dto.CreatePaymentRequest
import com.rentalhub.backend.payment.dto.PaymentResponse
import com.rentalhub.backend.tenant.TenantRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val leaseRepository: LeaseRepository,
    private val landlordRepository: LandlordRepository,
    private val tenantRepository: TenantRepository
) {
    private fun authEmail(): String =
        SecurityContextHolder.getContext().authentication.name

    private fun currentLandlordId(): Long =
        landlordRepository.findByEmail(authEmail())?.id
            ?: throw IllegalStateException("Authenticated landlord not found")

    private fun currentTenantId(): Long =
        tenantRepository.findByEmail(authEmail())?.id
            ?: throw IllegalStateException("Authenticated tenant not found")

    /** LANDLORD: list payments across all properties they own */
    fun listForLandlord(): List<PaymentResponse> =
        paymentRepository.findAllByLease_Unit_Property_Landlord_Id(currentLandlordId())
            .map { it.toResponse() }

    /** TENANT: list my payments across my leases */
    fun listForTenant(): List<PaymentResponse> =
        paymentRepository.findAllByLease_Tenant_Id(currentTenantId())
            .map { it.toResponse() }

    /** TENANT: create a payment for one of my leases */
    @Transactional
    fun create(req: CreatePaymentRequest): PaymentResponse {
        val tenantId = currentTenantId()
        val lease = leaseRepository.findById(req.leaseId)
            .orElseThrow { NoSuchElementException("Lease not found: ${req.leaseId}") }

        require(lease.tenant.id == tenantId) { "Forbidden: lease does not belong to you" }

        val saved = paymentRepository.save(
            Payment(
                amount = req.amount,
                datePaid = req.datePaid ?: LocalDate.now(),
                lease = lease
            )
        )
        return saved.toResponse()
    }

    private fun Payment.toResponse() = PaymentResponse(
        id = this.id,
        leaseId = this.lease.id,
        amount = this.amount,
        datePaid = this.datePaid
    )
}
