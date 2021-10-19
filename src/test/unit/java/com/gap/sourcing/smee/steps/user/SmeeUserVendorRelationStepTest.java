package com.gap.sourcing.smee.steps.user;


import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.enums.RequestAction;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.exceptions.GenericUnknownActionException;
import com.gap.sourcing.smee.repositories.SmeeUserTypeRepository;
import com.gap.sourcing.smee.steps.Step;
import com.gap.sourcing.smee.utils.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import providers.ResourceProvider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)

public class SmeeUserVendorRelationStepTest {

    @Mock
    Step smeeUserEntityMergeStep;

    @Mock
    Client client;

    SmeeUserVendorRelationStep smeeUserVendorRelationStep;

    private SmeeUserContext context;
    SmeeUser entity;

    @BeforeEach
    void init() {
        smeeUserVendorRelationStep = new SmeeUserVendorRelationStep(smeeUserEntityMergeStep,
                client);
        SmeeUserCreateResource resource = new SmeeUserCreateResource();//ResourceProvider.getSmeeUserCreateResource();
        context = new SmeeUserContext(resource);
        entity = new SmeeUser();
        entity.setUserName("xyz");
        entity.setUserEmail("xyz@abc.com");
        context.setInput(entity);
    }


    @Test
    void execute_shouldReturnASmeeUserEntityMergeStepStep() throws GenericUserException {
       // final SmeeUserCreateResource resource = ResourceProvider.getSmeeUserCreateResource();
      //  final SmeeUserContext context = new SmeeUserContext(resource);
        final Step step = smeeUserVendorRelationStep.execute(context);

        assertThat(step, is(smeeUserEntityMergeStep));
    }
}
