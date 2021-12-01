package com.gap.sourcing.smee.services.impl;

import com.gap.sourcing.smee.entities.SmeeUserType;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.repositories.SmeeUserTypeRepository;
import com.gap.sourcing.smee.services.SmeeUserTypeLoadService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Log4j2
@Service
public class SmeeUserTypeLoadServiceImpl implements SmeeUserTypeLoadService {
    private final SmeeUserTypeRepository smeeUserTypeRepository;

    public SmeeUserTypeLoadServiceImpl(final SmeeUserTypeRepository smeeUserTypeRepository) {
        this.smeeUserTypeRepository = smeeUserTypeRepository;
    }

    @Cacheable(value = "smee-usertypes", unless = "#result == T(java.util.Collections).emptyList()")
    public List<SmeeUserType> getSmeeUserTypes() throws GenericUserException {
        List<SmeeUserType> smeeUserTypesFromDb  = smeeUserTypeRepository.findAll();
        log.info("Fetched User-Types From DB:: ", kv("smeeUserTypesFromDb", smeeUserTypesFromDb));

        return smeeUserTypesFromDb;

    }

    public String fetchUserTypeFromCache(long userTypeId){
        List<SmeeUserType> smeeUserTypesFromCache  = getSmeeUserTypes();
        Optional<SmeeUserType> smeeUserTypeVal  =   smeeUserTypesFromCache
                .stream().
                filter(userType -> userType.getId().equals(userTypeId)).findFirst();

          return   smeeUserTypeVal.get().getUserType();

    }

}
