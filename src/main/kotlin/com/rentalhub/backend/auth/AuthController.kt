package com.rentalhub.backend.auth

import com.rentalhub.backend.landlord.Landlord
import com.rentalhub.backend.landlord.LandlordRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
            Landlord(name = req.email.split("@")[0], email = req.email, password = passwordEncoder.encode(req.password))
        )
        val token = jwtUtil.generateToken(landlord.email, "LANDLORD")
        return AuthResponse(token)
    }

    @PostMapping("/login")
    fun login(@RequestBody req: AuthRequest): AuthResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(req.email, req.password)
        )
        return AuthResponse(jwtUtil.generateToken(req.email, "LANDLORD"))
    }
}