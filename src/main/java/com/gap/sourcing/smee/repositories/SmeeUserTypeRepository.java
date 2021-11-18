package com.gap.sourcing.smee.repositories;

import com.gap.sourcing.smee.entities.SmeeUserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmeeUserTypeRepository extends JpaRepository<SmeeUserType, Long> {

    SmeeUserType  findSmeeUserTypeByUserType(String userType);
    List<SmeeUserType> findAll();
}
