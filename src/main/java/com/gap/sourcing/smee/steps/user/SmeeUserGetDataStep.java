package com.gap.sourcing.smee.steps.user;


import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserGetResource;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.entities.SmeeUserType;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.exceptions.ResourceNotFoundException;
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

        log.info("Getting user details for user-id, resource={}", resource, kv(REQUEST_ID_KEY, MDC.get(REQUEST_ID_KEY)));

        SmeeUser smeeUser = smeeUserRepository.findSmeeUserByUserName(userIdToGetDetails);
        if (smeeUser != null) {
            Optional<SmeeUserType> smeeUserType = smeeUserTypeRepository.findById(smeeUser.getUserTypeId());
            if (smeeUserType.isPresent()) {
                userContext.setSmeeUserType(smeeUserType.get().getUserType());
            }
            ((SmeeUserContext) context).setOutput(smeeUser);
        } else {
            userContext.setOutput(null);
            throw new ResourceNotFoundException(resource, String.format("Passed user id : %s not found", resource.getUserId()));
        }
        return smeeUserResponseConversionStep;
    }

}
