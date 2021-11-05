package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.Resource;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.dtos.resources.SmeeUserGetResource;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.entities.SmeeUserType;
import com.gap.sourcing.smee.exceptions.GenericBadRequestException;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.repositories.SmeeUserRepository;
import com.gap.sourcing.smee.repositories.SmeeUserTypeRepository;
import com.gap.sourcing.smee.steps.Step;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
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
                smeeUserRepository, smeeUserTypeRepository);
    }


  /*  @Test
    void execute_shouldThrowExceptionWhenUserIdisNull() throws GenericUserException {
        Assertions.assertThrows(GenericBadRequestException.class, () -> smeeUserGetDataStep.execute(context));

    }*/


    @Test
    void execute_shouldReturnAsmeeUserResponseConversionStep() throws GenericUserException {
        resource.setUserId("abh");
        final Step step = smeeUserGetDataStep.execute(context);
        assertThat(step, is(smeeUserResponseConversionStep));
    }

    @Test
    void execute_shouldReturnAsmeeUserResponse() throws GenericUserException {
        resource.setUserId("abh");

        SmeeUserType smeeUserType = new SmeeUserType();
        SmeeUser smeeUser = new SmeeUser();

        Long id = Long.valueOf(1);
        smeeUser.setUserTypeId(id);
        when(smeeUserRepository.findSmeeUserByUserName("abh")).thenReturn(smeeUser);
        when(smeeUserTypeRepository.findById(id)).thenReturn(java.util.Optional.of(smeeUserType));
        final Step step = smeeUserGetDataStep.execute(context);
        assertThat(step, is(smeeUserResponseConversionStep));
    }
}
