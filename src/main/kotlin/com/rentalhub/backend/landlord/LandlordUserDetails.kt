package com.rentalhub.backend.landlord

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class LandlordUserDetails(private val landlord: Landlord) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> =
        listOf(SimpleGrantedAuthority("ROLE_LANDLORD"))

    override fun getPassword() = landlord.password
    override fun getUsername() = landlord.email

    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}