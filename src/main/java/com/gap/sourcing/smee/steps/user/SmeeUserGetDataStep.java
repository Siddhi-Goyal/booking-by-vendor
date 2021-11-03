package com.gap.sourcing.smee.steps.user;


import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserGetResource;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.entities.SmeeUserType;
import com.gap.sourcing.smee.exceptions.GenericBadRequestException;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.repositories.SmeeUserRepository;
import com.gap.sourcing.smee.repositories.SmeeUserTypeRepository;
import com.gap.sourcing.smee.steps.Step;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.gap.sourcing.smee.utils.RequestIdGenerator.REQUEST_ID_KEY;
import static net.logstash.logback.argument.StructuredArguments.kv;

@Component
@Slf4j
public class SmeeUserGetDataStep implements Step {

    private final Step smeeUserResponseConversionStep;
    private final SmeeUserRepository smeeUserRepository;
    private final SmeeUserTypeRepository smeeUserTypeRepository;

    public SmeeUserGetDataStep(Step smeeUserResponseConversionStep, SmeeUserRepository smeeUserRepository,
                               SmeeUserTypeRepository smeeUserTypeRepository) {
        this.smeeUserRepository = smeeUserRepository;
        this.smeeUserResponseConversionStep = smeeUserResponseConversionStep;
        this.smeeUserTypeRepository = smeeUserTypeRepository;
    }

    public Step execute(Context context) throws GenericUserException {
        SmeeUserContext userContext = (SmeeUserContext) context;
        SmeeUserGetResource resource = (SmeeUserGetResource) userContext.getResource();
        String userIdToGetDetails = resource.getUserId();
        if(userIdToGetDetails==null){
            log.info("User Id is cannot be null",
                    kv("userId", resource.getUserId()), kv(REQUEST_ID_KEY, MDC.get(REQUEST_ID_KEY)));
            throw new GenericBadRequestException(resource, "User Id is cannot be null");
        }
        Optional<SmeeUser> smeeUser;
        log.info("Getting user details for user-id, resource={}", resource, kv(REQUEST_ID_KEY, MDC.get(REQUEST_ID_KEY)));

        try {
            smeeUser = Optional.ofNullable(smeeUserRepository.findSmeeUserByUserName(userIdToGetDetails));
            if (smeeUser.isPresent()) {
                Long userTypeId = smeeUser.get().getUserTypeId();
                Optional<SmeeUserType> smeeUserType = smeeUserTypeRepository.findById(userTypeId);
                if(smeeUserType.isPresent()) {
                    String userType = smeeUserType.get().getUserType();
                    userContext.setSmeeUserType(userType);
                }
                ((SmeeUserContext) context).setOutput(smeeUser.get());
            } else {
                userContext.setOutput(null);
            }
        } catch (Exception exception) {
            throw new GenericBadRequestException(resource, "User-id passed is null");
        }
        return smeeUserResponseConversionStep;
    }

}
