package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.UserContext;
import com.gap.sourcing.smee.dtos.resources.UserCreateResource;
import com.gap.sourcing.smee.exceptions.GenericBadRequestException;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.steps.Step;
import com.gap.sourcing.smee.steps.StepManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Component
@Slf4j
public class SmeeUserCreateValidationStep implements Step{

    private final Step userCreateResourceConversionStep;

    public SmeeUserCreateValidationStep(final Step userCreateResourceConversionStep) {
        this.userCreateResourceConversionStep = userCreateResourceConversionStep;
    }

    @Override
    public Step execute(final Context context) throws GenericUserException {
        final UserCreateResource resource = (UserCreateResource) ((UserContext) context).getResource();

        log.info("Validating the incoming resource for user creation", kv("resource", resource));

        // include validation if required

        log.info("Validation of the incoming resource is complete.");

        return userCreateResourceConversionStep;
    }

    private void validateInDcDate(UserCreateResource resource) throws GenericBadRequestException {

    }

}

