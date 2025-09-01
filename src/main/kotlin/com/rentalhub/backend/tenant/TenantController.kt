package com.rentalhub.backend.tenant

import com.rentalhub.backend.tenant.dto.CreateTenantRequest
import com.rentalhub.backend.tenant.dto.TenantResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tenants")
@PreAuthorize("hasRole('LANDLORD')")
class TenantController(
    private val tenantService: TenantService
) {
    @GetMapping
    fun list(): List<TenantResponse> = tenantService.listTenants()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): TenantResponse = tenantService.getTenant(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody req: CreateTenantRequest): TenantResponse =
        tenantService.createTenant(req)
}
