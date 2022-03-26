package com.gap.sourcing.smee.repositories;

import com.gap.sourcing.smee.entities.SmeeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SmeeUserRepository extends JpaRepository<SmeeUser, Integer> {

    SmeeUser findSmeeUserByUserName(String userName);

    //TODO: Included this to prove it's working, to be deleted once tested
    @Query(value = "SELECT cast(DATABASEPROPERTYEX(DB_NAME(), 'Updateability') as varchar);", nativeQuery = true)
    String checkReplica();

}
