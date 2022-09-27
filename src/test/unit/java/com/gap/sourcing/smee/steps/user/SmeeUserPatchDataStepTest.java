package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserGetResource;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.entities.SmeeUserType;
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

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SmeeUserPatchDataStepTest {


    @Mock
    SmeeUserRepository smeeUserRepository;

    private SmeeUserPatchDataStep smeeUserPatchDataStep;

    private SmeeUserContext context;
    SmeeUser entity;

    private SmeeUserGetResource resource;

    @BeforeEach
    void init() {
        SmeeUserType type = SmeeUserType.builder().id(UUID.randomUUID()).build();
        resource = new SmeeUserGetResource();
        context = new SmeeUserContext(resource);
        entity = new SmeeUser();
        entity.setUserName("xyz");
        entity.setUserEmail("xyz@abc.com");
        entity.setIsAdmin(false);
        entity.setUserTypeId(type);
        context.setInput(entity);

        context = new SmeeUserContext(null);

        smeeUserPatchDataStep = new SmeeUserPatchDataStep(
                smeeUserRepository);
    }


    @Test
    void execute_shouldReturnNullWhenNoUserNameIsFound() throws GenericUserException {
        resource.setUserName("xyz");
        context.setResource(resource);
        entity = null;
        when(smeeUserRepository.findSmeeUserByUserName("xyz")).thenReturn(entity);
        final Step step = smeeUserPatchDataStep.execute(context);
        assertThat(context.getOutput(), is(nullValue()));

    }

    @Test
    void execute_shouldReturnAsmeeUserResponseWhenUserNameIsFound() throws GenericUserException {
        resource.setUserName("xyz");
        context.setResource(resource);
        when(smeeUserRepository.findSmeeUserByUserName("xyz")).thenReturn(entity);
        entity.setLastLoginDate(ZonedDateTime.now());
        final Step step = smeeUserPatchDataStep.execute(context);
        assertNull(step);
    }
}
