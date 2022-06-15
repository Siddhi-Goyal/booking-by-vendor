package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserGetResource;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.entities.SmeeUserType;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.repositories.SmeeUserRepository;
import com.gap.sourcing.smee.repositories.SmeeUserTypeRepository;
import com.gap.sourcing.smee.services.SmeeUserTypeLoadService;
import com.gap.sourcing.smee.steps.Step;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        SmeeUserType type = SmeeUserType.builder().id(UUID.randomUUID()).build();
        resource = new SmeeUserGetResource();
        context = new SmeeUserContext(resource);
        entity = new SmeeUser();
        entity.setUserName("xyz");
        entity.setUserEmail("xyz@abc.com");
        entity.setUserTypeId(type);
        context.setInput(entity);

        context = new SmeeUserContext(null);
        userTypeEntity = new ArrayList<SmeeUserType>();
        SmeeUserType sut = new SmeeUserType();
        sut.setId(type.getId());
        sut.setUserType("GIS PD");
        sut.setDescription("Test");
        userTypeEntity.add(sut);

        context.setUserTypeOutput(userTypeEntity);
        smeeUserGetDataStep = new SmeeUserGetDataStep(smeeUserResponseConversionStep,
                smeeUserRepository, smeeUserTypeLoadService);
        SmeeUser smeeUser = new SmeeUser();
        smeeUser.setUserTypeId(type);


    }

    @Test
    void execute_shouldReturnAsmeeUserResponseConversionStep() throws GenericUserException {
        resource.setUserName("xyz");
        context.setResource(resource);
        final Step step = smeeUserGetDataStep.execute(context);
        assertThat(step, is(smeeUserResponseConversionStep));
    }


    @Test
    void execute_shouldReturnNullWhenNoUserIdisFound() throws GenericUserException {
        resource.setUserName("xyz");
        context.setResource(resource);
        entity = null;
        when(smeeUserRepository.findSmeeUserByUserName("xyz")).thenReturn(entity);
        final Step step = smeeUserGetDataStep.execute(context);
        assertThat(context.getOutput(), is(nullValue()));

    }

    @Test
    void execute_shouldReturnAsmeeUserResponse() throws GenericUserException {
        resource.setUserName("xyz");
        context.setResource(resource);
        when(smeeUserRepository.findSmeeUserByUserName("xyz")).thenReturn(entity);
        final Step step = smeeUserGetDataStep.execute(context);
        assertThat(step, is(smeeUserResponseConversionStep));
    }
}
