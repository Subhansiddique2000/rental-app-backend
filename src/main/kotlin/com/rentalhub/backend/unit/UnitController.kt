package com.rentalhub.backend.unit

import com.rentalhub.backend.unit.dto.CreateUnitRequest
import com.rentalhub.backend.unit.dto.UpdateUnitRequest
import com.rentalhub.backend.unit.dto.UnitResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@PreAuthorize("hasRole('LANDLORD')")
class UnitController(
    private val unitService: RentalUnitService
) {
    @GetMapping("/properties/{propertyId}/units")
    fun list(@PathVariable propertyId: Long): List<UnitResponse> =
        unitService.listUnits(propertyId)

    @PostMapping("/properties/{propertyId}/units")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @PathVariable propertyId: Long,
        @Valid @RequestBody req: CreateUnitRequest
    ): UnitResponse = unitService.create(propertyId, req)

    @PutMapping("/units/{unitId}")
    fun update(
        @PathVariable unitId: Long,
        @Valid @RequestBody req: UpdateUnitRequest
    ): UnitResponse = unitService.update(unitId, req)

    @DeleteMapping("/units/{unitId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable unitId: Long) = unitService.delete(unitId)
}
