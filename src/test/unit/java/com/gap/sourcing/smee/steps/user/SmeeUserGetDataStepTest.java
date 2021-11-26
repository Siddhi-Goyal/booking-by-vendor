package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserGetResource;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.exceptions.ResourceNotFoundException;
import com.gap.sourcing.smee.repositories.SmeeUserRepository;
import com.gap.sourcing.smee.repositories.SmeeUserTypeRepository;
import com.gap.sourcing.smee.services.SmeeUserTypeLoadService;
import com.gap.sourcing.smee.steps.Step;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SmeeUserGetDataStepTest {

    @Mock
    SmeeUserRepository smeeUserRepository;

    @Mock
    SmeeUserTypeRepository smeeUserTypeRepository;

    @Mock
    SmeeUserResponseConversionStep smeeUserResponseConversionStep;

    @Mock
    SmeeUserTypeLoadService smeeUserTypeLoadService;

    private SmeeUserGetDataStep smeeUserGetDataStep;

    private SmeeUserContext context;
    SmeeUser entity;

    private SmeeUserGetResource resource;

    @BeforeEach
    void init() {

        resource = new SmeeUserGetResource();
        context = new SmeeUserContext(resource);
        entity = new SmeeUser();
        entity.setUserName("xyz");
        entity.setUserEmail("xyz@abc.com");
        context.setInput(entity);
        smeeUserGetDataStep = new SmeeUserGetDataStep(smeeUserResponseConversionStep,
                smeeUserRepository, smeeUserTypeLoadService);

        SmeeUser smeeUser = new SmeeUser();

        smeeUser.setUserTypeId(1L);

    }

    @Test
    void execute_shouldReturnAsmeeUserResponseConversionStep() throws GenericUserException {
        resource.setUserId("testId");

        final Step step = smeeUserGetDataStep.execute(context);
        assertThat(step, is(smeeUserResponseConversionStep));
    }

    @Test
    void execute_shouldReturnAsmeeUserResponse() throws GenericUserException {
        resource.setUserId("testId");

        final Step step = smeeUserGetDataStep.execute(context);
        assertThat(step, is(smeeUserResponseConversionStep));
    }

    @Test
    void execute_shouldReturnSmeeUserEmptyResponseWhenNoUserId() throws GenericUserException {
        final SmeeUserContext context = new SmeeUserContext(null);
        final Step step = smeeUserResponseConversionStep.execute(context);
        assertThat(step, is(nullValue()));
    }
}
