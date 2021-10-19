package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.enums.RequestAction;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.exceptions.GenericUnknownActionException;
import com.gap.sourcing.smee.repositories.SmeeUserRepository;
import org.junit.jupiter.api.MethodOrderer;
import com.gap.sourcing.smee.repositories.SmeeUserTypeRepository;
import com.gap.sourcing.smee.steps.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import providers.ResourceProvider;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SmeeUserLoadDataStepTest {

    @Mock
    Step smeeUserVendorRelationStep;

    @Mock
    SmeeUserRepository smeeUserRepository;

    private SmeeUserLoadDataStep smeeUserLoadDataStep;

    private SmeeUserContext context;
    SmeeUser entity;

    @BeforeEach
    void init() {

        SmeeUserCreateResource resource = new SmeeUserCreateResource();//ResourceProvider.getSmeeUserCreateResource();
        context = new SmeeUserContext(resource);
        entity = new SmeeUser();
        entity.setUserName("xyz");
        entity.setUserEmail("xyz@abc.com");

        context.setInput(entity);
        smeeUserLoadDataStep = new SmeeUserLoadDataStep(smeeUserVendorRelationStep,
                smeeUserRepository);
    }


    @Test
    void execute_shouldReturnASmeeUserVendorRelationStep() throws GenericUserException {
         SmeeUser entityFromDb = new SmeeUser();
        // entityFromDb.setUserName("xyz");
         final Step step = smeeUserLoadDataStep.execute(context);
     //   when(smeeUserRepository.findSmeeUserByUserName(entity.getUserName())).thenReturn(entityFromDb);
        assertThat(step, is(smeeUserVendorRelationStep));
    }

}
