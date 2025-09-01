package com.rentalhub.backend.auth

import com.rentalhub.backend.landlord.LandlordRepository
import com.rentalhub.backend.landlord.LandlordUserDetails
import com.rentalhub.backend.tenant.TenantRepository
import com.rentalhub.backend.tenant.TenantUserDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AppUserDetailsService(
    private val landlordRepository: LandlordRepository,
    private val tenantRepository: TenantRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        landlordRepository.findByEmail(email)?.let { return LandlordUserDetails(it) }
        tenantRepository.findByEmail(email)?.let { return TenantUserDetails(it) }
        throw UsernameNotFoundException("User not found for email: $email")
    }
}
