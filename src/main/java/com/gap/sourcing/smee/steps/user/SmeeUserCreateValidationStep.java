package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.entities.SmeeUserType;
import com.gap.sourcing.smee.exceptions.GenericBadRequestException;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.repositories.SmeeUserTypeRepository;
import com.gap.sourcing.smee.steps.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Component
@Slf4j
public class SmeeUserCreateValidationStep implements Step{

    private static final String INVALID_USER_TYPE_ERROR_MESSAGE = "Received invalid user type in request, user type should be";

    private final Step smeeUserCreateResourceConversionStep;
    private final SmeeUserTypeRepository smeeUserTypeRepository;

    public SmeeUserCreateValidationStep(final Step smeeUserCreateResourceConversionStep, SmeeUserTypeRepository smeeUserTypeRepository) {
        this.smeeUserCreateResourceConversionStep = smeeUserCreateResourceConversionStep;
        this.smeeUserTypeRepository = smeeUserTypeRepository;
    }

    @Override
    public Step execute(final Context context) throws GenericUserException {
        final SmeeUserCreateResource resource = (SmeeUserCreateResource) ((SmeeUserContext) context).getResource();

        log.info("Validating the incoming resource for user creation", kv("resource", resource));

        List<SmeeUserType> smeeUserTypes = smeeUserTypeRepository.findAll();
        Optional<SmeeUserType> smeeUserType =  smeeUserTypes.stream().filter(userType -> resource.getUserType()
                .equals(userType.getUserType())).findAny();
        if(smeeUserType.isEmpty()) {
            List<String> userTypes = smeeUserTypes.stream().map(SmeeUserType::getUserType).collect(Collectors.toList());
            log.info(INVALID_USER_TYPE_ERROR_MESSAGE, userTypes,
                    kv("userType", resource.getUserType()));
            throw new GenericBadRequestException(resource, INVALID_USER_TYPE_ERROR_MESSAGE + userTypes);
        }

        log.info("Validation of the incoming resource is complete.");

        return smeeUserCreateResourceConversionStep;
    }

}
