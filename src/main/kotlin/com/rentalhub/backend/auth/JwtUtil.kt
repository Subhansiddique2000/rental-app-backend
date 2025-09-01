package com.rentalhub.backend.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import java.util.Base64

@Component
class JwtUtil(
    @Value("\${security.jwt.secret}") private val secretB64: String,
    @Value("\${security.jwt.ttl-hours:24}") private val ttlHours: Long
) {
    // Build the HMAC key once from the Base64 secret
    private val key by lazy {
        val bytes = Base64.getDecoder().decode(secretB64)
        Keys.hmacShaKeyFor(bytes)
    }

    fun generateToken(email: String, role: String): String {
        val now = Date()
        val expiry = Date(now.time + ttlHours * 60 * 60 * 1000)

        return Jwts.builder()
            .setSubject(email)
            .claim("role", role)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(key)
            .compact()
    }

    fun extractEmail(token: String): String =
        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
}
