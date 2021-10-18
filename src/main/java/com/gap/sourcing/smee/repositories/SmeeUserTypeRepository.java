package com.gap.sourcing.smee.repositories;

import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.entities.SmeeUserType;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SmeeUserTypeRepository extends JpaRepository<SmeeUserType, Integer> {
   //  SmeeUserType  findSmeeUserType(String userType);




}
