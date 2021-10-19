package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.repositories.SmeeUserTypeRepository;
import com.gap.sourcing.smee.steps.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import providers.ResourceProvider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(MockitoExtension.class)

public class SmeeUserCreateResourceConversionStepTest {

    @Mock
    Step smeeUserLoadDataStep;

    @Mock
    SmeeUserTypeRepository smeeUserTypeRepository;

    private SmeeUserCreateResourceConversionStep smeeUserCreateResourceConversionStep;

    @BeforeEach
    void init() {
        smeeUserCreateResourceConversionStep = new SmeeUserCreateResourceConversionStep(smeeUserLoadDataStep,
                smeeUserTypeRepository);
    }

    @Test
    void execute_shouldReturnASmeeUserLoadDataStep() throws GenericUserException {
        final SmeeUserCreateResource resource = ResourceProvider.getSmeeUserCreateResource();
        final SmeeUserContext context = new SmeeUserContext(resource);
        final Step step = smeeUserCreateResourceConversionStep.execute(context);

        assertThat(step, is(smeeUserLoadDataStep));
    }
}
