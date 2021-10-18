package com.gap.sourcing.smee.repositories;

import com.gap.sourcing.smee.entities.SmeeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmeeUserRepository extends JpaRepository<SmeeUser, Integer> {

    SmeeUser findSmeeUserByUserName(String userName);
}
