package com.rentalhub.backend.lease

import org.springframework.data.jpa.repository.JpaRepository

interface LeaseRepository : JpaRepository<Lease, Long>