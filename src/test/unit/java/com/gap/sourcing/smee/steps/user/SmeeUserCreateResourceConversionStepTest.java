package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.entities.SmeeUserType;
import com.gap.sourcing.smee.exceptions.GenericBadRequestException;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.repositories.SmeeUserTypeRepository;
import com.gap.sourcing.smee.steps.Step;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import providers.ResourceProvider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SmeeUserCreateResourceConversionStepTest {

    @Mock
    Step smeeUserLoadDataStep;

    @Mock
    SmeeUserTypeRepository smeeUserTypeRepository;

    private SmeeUserType smeeUserType;

    private SmeeUserCreateResourceConversionStep smeeUserCreateResourceConversionStep;

    @BeforeEach
    void init() {
        smeeUserType = new SmeeUserType();
        smeeUserType.setId(1);
        smeeUserCreateResourceConversionStep = new SmeeUserCreateResourceConversionStep(smeeUserLoadDataStep,
                smeeUserTypeRepository);
    }

    @Test
    void execute_shouldReturnASmeeUserLoadDataStep() throws GenericUserException {
        final SmeeUserCreateResource resource = ResourceProvider.getSmeeUserCreateResource();
        final SmeeUserContext context = new SmeeUserContext(resource);
        when(smeeUserTypeRepository.findSmeeUserTypeByUserType(resource.getUserType())).thenReturn(smeeUserType);
        final Step step = smeeUserCreateResourceConversionStep.execute(context);

        assertThat(step, is(smeeUserLoadDataStep));
    }

    @Test
    void execute_shouldUpdateTheContextWithAnInputSmeeUserEntityObject() throws GenericUserException {
        final SmeeUserCreateResource resource = ResourceProvider.getSmeeUserCreateResource();
        final SmeeUserContext context = new SmeeUserContext(resource);
        when(smeeUserTypeRepository.findSmeeUserTypeByUserType(resource.getUserType())).thenReturn(smeeUserType);
        smeeUserCreateResourceConversionStep.execute(context);
        final SmeeUser input = context.getInput();

        assertThat(input, is(notNullValue()));
    }

    @Test
    void execute_shouldReturnASmeeUserLoadDataStep_exception() throws GenericUserException {
        final SmeeUserCreateResource resource = ResourceProvider.getSmeeUserCreateResource();
        final SmeeUserContext context = new SmeeUserContext(null);

        Assertions.assertThrows(GenericBadRequestException.class, () -> smeeUserCreateResourceConversionStep.execute(context));

    }
}
