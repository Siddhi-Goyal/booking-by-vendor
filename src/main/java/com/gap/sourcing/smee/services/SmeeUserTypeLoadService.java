package com.gap.sourcing.smee.services;

import com.gap.sourcing.smee.entities.SmeeUserType;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.UUID;

public interface SmeeUserTypeLoadService {

    @Cacheable(value = "smee-usertypes", unless = "#result == T(java.util.Collections).emptyList()")
    List<SmeeUserType> getSmeeUserTypes();

    String fetchUserTypeFromCache(UUID userTypeId);


}
