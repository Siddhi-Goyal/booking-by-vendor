package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.enums.RequestAction;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.exceptions.GenericUnknownActionException;
import com.gap.sourcing.smee.steps.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import providers.ResourceProvider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)

public class SmeeUserCreateValidationStepTest {

    @Mock
    SmeeUserCreateResourceConversionStep smeeUserCreateResourceConversionStep;

    private Step smeeUserCreateValidationStep;

    private SmeeUserStepManager smeeUserStepManager;

    @BeforeEach
    void init() {
        smeeUserCreateValidationStep = new SmeeUserCreateValidationStep(smeeUserCreateResourceConversionStep);
    }

    @Test
    void execute_shouldReturnASmeeUserCreateResourceConversionStepGivenAValidResource() throws GenericUserException {
        final SmeeUserCreateResource resource = ResourceProvider.getSmeeUserCreateResource();
        final SmeeUserContext context = new SmeeUserContext(resource);
        final Step step = smeeUserCreateValidationStep.execute(context);
        assertThat(step, is(smeeUserCreateResourceConversionStep));
    }
}


