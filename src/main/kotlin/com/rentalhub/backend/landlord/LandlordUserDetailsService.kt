package com.rentalhub.backend.landlord

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class LandlordUserDetailsService(
    private val landlordRepository: LandlordRepository
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val landlord = landlordRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found")
        return LandlordUserDetails(landlord)
    }
}