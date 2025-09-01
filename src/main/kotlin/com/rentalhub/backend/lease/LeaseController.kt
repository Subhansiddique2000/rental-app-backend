package com.rentalhub.backend.lease

import com.rentalhub.backend.lease.dto.CreateLeaseRequest
import com.rentalhub.backend.lease.dto.LeaseResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class LeaseController(
    private val leaseService: LeaseService
) {
    /** Landlord endpoints */
    @GetMapping("/leases")
    @PreAuthorize("hasRole('LANDLORD')")
    fun listForLandlord(): List<LeaseResponse> = leaseService.listLeasesForLandlord()

    @PostMapping("/leases")
    @PreAuthorize("hasRole('LANDLORD')")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody req: CreateLeaseRequest): LeaseResponse =
        leaseService.create(req)

    /** Tenant endpoint — will work after tenant auth is added */
    @GetMapping("/leases/my")
    @PreAuthorize("hasRole('TENANT')")
    fun myLeases(@AuthenticationPrincipal principal: User): List<LeaseResponse> =
        leaseService.listMyLeasesForTenant(principal.username)
}
