package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.steps.Step;
import com.gap.sourcing.smee.steps.helper.SmeeUserEntityToDTOConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import providers.ResourceProvider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

@ExtendWith(MockitoExtension.class)

public class SmeeUserResponseConversionStepTest {

    @Mock
    SmeeUserEntityToDTOConverter smeeUserEntityToDTOConverter;

    private SmeeUserResponseConversionStep smeeUserResponseConversionStep;

    @BeforeEach
    void init() {

        smeeUserResponseConversionStep = new SmeeUserResponseConversionStep(smeeUserEntityToDTOConverter);
    }

    @Test
    void execute_shouldReturnResponseInContext() throws GenericUserException {
        final SmeeUserCreateResource resource = ResourceProvider.getSmeeUserCreateResource();
        final SmeeUserContext context = new SmeeUserContext(resource);
        final Step step = smeeUserResponseConversionStep.execute(context);
        assertThat(step, is(nullValue()));
    }


}
