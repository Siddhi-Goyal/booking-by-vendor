package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.exceptions.GenericBadRequestException;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.steps.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Component
@Slf4j
public class SmeeUserCreateValidationStep implements Step{

    private final Step smeeUserCreateResourceConversionStep;

    public SmeeUserCreateValidationStep(final Step smeeUserCreateResourceConversionStep) {
        this.smeeUserCreateResourceConversionStep = smeeUserCreateResourceConversionStep;
    }

    @Override
    public Step execute(final Context context) throws GenericUserException {
        final SmeeUserCreateResource resource = (SmeeUserCreateResource) ((SmeeUserContext) context).getResource();

        log.info("Validating the incoming resource for user creation", kv("resource", resource));

        // include validation if required

        log.info("Validation of the incoming resource is complete.");

        return smeeUserCreateResourceConversionStep;
    }



}

