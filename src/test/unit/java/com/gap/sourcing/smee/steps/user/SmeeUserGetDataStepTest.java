package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.dtos.resources.SmeeUserGetResource;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.entities.SmeeUserType;
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
import providers.ResourceProvider;

import java.util.ArrayList;
import java.util.List;

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

    List<SmeeUserType> userTypeEntity;

    @BeforeEach
    void init() {

        resource = new SmeeUserGetResource();
        context = new SmeeUserContext(resource);
        entity = new SmeeUser();
        entity.setUserName("xyz");
        entity.setUserEmail("xyz@abc.com");
        entity.setUserTypeId(1L);
        context.setInput(entity);

        context = new SmeeUserContext(null);
        userTypeEntity = new ArrayList<SmeeUserType>();
        SmeeUserType sut = new SmeeUserType();
        sut.setId(1L);
        sut.setUserType("GIS PD");
        sut.setDescription("Test");
        userTypeEntity.add(sut);

        context.setUserTypeOutput(userTypeEntity);
        smeeUserGetDataStep = new SmeeUserGetDataStep(smeeUserResponseConversionStep,
                smeeUserRepository, smeeUserTypeLoadService);
        SmeeUser smeeUser = new SmeeUser();
        smeeUser.setUserTypeId(1L);


    }

    @Test
    void execute_shouldReturnAsmeeUserResponseConversionStep() throws GenericUserException {
        resource.setUserId("xyz");
        context.setResource(resource);
        final Step step = smeeUserGetDataStep.execute(context);
        assertThat(step, is(smeeUserResponseConversionStep));
    }


    @Test
    void execute_shouldReturnSmeeUserEmptyResponseWhenNoUserId() throws GenericUserException {
        final SmeeUserContext context = new SmeeUserContext(null);
        final Step step = smeeUserResponseConversionStep.execute(context);
        assertThat(step, is(nullValue()));
    }

    @Test
    void execute_shouldReturnAsmeeUserResponse1() throws GenericUserException {
        resource.setUserId("xyz");
        context.setResource(resource);
        when(smeeUserTypeLoadService.getSmeeUserTypes()).thenReturn(context.getUserTypeOutput());
        when(smeeUserRepository.findSmeeUserByUserName("xyz")).thenReturn(entity);
        final Step step = smeeUserGetDataStep.execute(context);
        assertThat(step, is(smeeUserResponseConversionStep));
    }
}
