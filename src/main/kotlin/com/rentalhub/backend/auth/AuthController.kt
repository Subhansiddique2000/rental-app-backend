package com.rentalhub.backend.auth

import com.rentalhub.backend.landlord.Landlord
import com.rentalhub.backend.landlord.LandlordRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val landlordRepository: LandlordRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil
) {
    data class AuthRequest(val email: String, val password: String)
    data class AuthResponse(val token: String)

    @PostMapping("/signup")
    fun signup(@RequestBody req: AuthRequest): AuthResponse {
        val landlord = landlordRepository.save(
            Landlord(
                name = req.email.substringBefore("@"),
                email = req.email,
                password = passwordEncoder.encode(req.password)
            )
        )
        val token = jwtUtil.generateToken(landlord.email, "LANDLORD")
        return AuthResponse(token)
    }

    @PostMapping("/login")
    fun login(@RequestBody req: AuthRequest): AuthResponse {
        val auth = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(req.email, req.password)
        )
        // Grab the first authority and strip "ROLE_" for the token claim
        val role = auth.authorities.firstOrNull()?.authority?.removePrefix("ROLE_") ?: "LANDLORD"
        return AuthResponse(jwtUtil.generateToken(req.email, role))
    }

    // Optional convenience alias for tenants (uses the same logic)
    @PostMapping("/tenant/login")
    fun tenantLogin(@RequestBody req: AuthRequest): AuthResponse = login(req)
}
