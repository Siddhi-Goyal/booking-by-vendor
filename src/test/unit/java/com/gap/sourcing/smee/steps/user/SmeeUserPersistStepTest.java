package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.repositories.SmeeUserRepository;
import com.gap.sourcing.smee.steps.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import providers.ResourceProvider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SmeeUserPersistStepTest {

    @Mock
    Step smeeUserResponseConversionStep;
    @Mock
    SmeeUserRepository smeeUserRepository;

    private SmeeUserPersistStep smeeUserPersistStep;
    private SmeeUserCreateResource resource;
    private SmeeUserContext context;

    SmeeUser entity;

    @BeforeEach
    void init() {
        resource = ResourceProvider.getSmeeUserCreateResource();
        entity = new SmeeUser();
        context = new SmeeUserContext(resource);
        context.setInput(entity);
        smeeUserPersistStep = new SmeeUserPersistStep(smeeUserResponseConversionStep, smeeUserRepository);

    }

    @Test
    void execute_shouldReturnASmeeUserResponseConversionStep() throws GenericUserException {
        final SmeeUserCreateResource resource = ResourceProvider.getSmeeUserCreateResource();
        final SmeeUserContext context = new SmeeUserContext(resource);
        final Step step = smeeUserPersistStep.execute(context);

        assertThat(step, is(smeeUserResponseConversionStep));
    }

}
