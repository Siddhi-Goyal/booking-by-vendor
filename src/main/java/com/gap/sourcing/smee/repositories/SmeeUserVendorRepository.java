package com.gap.sourcing.smee.repositories;

import com.gap.sourcing.smee.entities.SmeeUserVendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmeeUserVendorRepository extends JpaRepository<SmeeUserVendor, Integer> {
    
}
