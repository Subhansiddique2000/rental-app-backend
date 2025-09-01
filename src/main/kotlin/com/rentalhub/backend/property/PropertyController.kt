package com.rentalhub.backend.property

import com.rentalhub.backend.property.dto.CreatePropertyRequest
import com.rentalhub.backend.property.dto.PropertyResponse
import com.rentalhub.backend.property.dto.UpdatePropertyRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/properties")
@PreAuthorize("hasRole('LANDLORD')")
class PropertyController(
    private val propertyService: PropertyService
) {
    @GetMapping
    fun list(): List<PropertyResponse> = propertyService.listMyProperties()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody req: CreatePropertyRequest): PropertyResponse =
        propertyService.create(req)

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody req: UpdatePropertyRequest
    ): PropertyResponse = propertyService.update(id, req)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) = propertyService.delete(id)
}
