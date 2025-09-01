package com.rentalhub.backend.auth

import org.springframework.stereotype.Component
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.util.*


@Component
class JwtUtil {
    private val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun generateToken(email: String, role: String): String { //lets make roll an enum
        val now = Date()
        val expiry = Date(now.time + 1000 * 60 * 60 * 24) // 24h

        return Jwts.builder()
            .setSubject(email)
            .claim("role", role)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(key)
            .compact()
    }

    fun extractEmail(token: String): String =
        Jwts.parserBuilder().setSigningKey(key).build()
            .parseClaimsJws(token).body.subject
}