package com.rentalhub.backend.payment

import com.rentalhub.backend.payment.dto.CreatePaymentRequest
import com.rentalhub.backend.payment.dto.PaymentResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class PaymentController(
    private val paymentService: PaymentService
) {
    // Landlord: list all payments for their properties
    @GetMapping("/payments")
    @PreAuthorize("hasRole('LANDLORD')")
    fun listForLandlord(): List<PaymentResponse> =
        paymentService.listForLandlord()

    // Tenant: list my payments
    @GetMapping("/payments/my")
    @PreAuthorize("hasRole('TENANT')")
    fun listForTenant(): List<PaymentResponse> =
        paymentService.listForTenant()

    // Tenant: create payment
    @PostMapping("/payments")
    @PreAuthorize("hasRole('TENANT')")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody req: CreatePaymentRequest): PaymentResponse =
        paymentService.create(req)
}
