package com.gap.sourcing.smee.steps.user;


import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.dtos.responses.denodo.DenodoElement;
import com.gap.sourcing.smee.dtos.responses.denodo.DenodoResponse;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.exceptions.GenericBadRequestException;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.steps.Step;
import com.gap.sourcing.smee.utils.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)

class SmeeUserBuildVendorRelationStepTest {

    @Mock
    Step smeeUserEntityMergeStep;

    @Mock
    Client client;

    SmeeUserBuildVendorRelationStep smeeUserVendorRelationStep;

    private SmeeUserContext context;
    SmeeUser entity;

    @BeforeEach
    void init() {
        smeeUserVendorRelationStep = new SmeeUserBuildVendorRelationStep(smeeUserEntityMergeStep,
                client);
        SmeeUserCreateResource resource = new SmeeUserCreateResource();
        context = new SmeeUserContext(resource);
        entity = new SmeeUser();
        entity.setUserName("xyz");
        entity.setUserEmail("xyz@abc.com");
        entity.setIsVendor(true);
        context.setInput(entity);
    }

    @Test
    void execute_shouldReturnASmeeUserEntityMergeStepStep() throws GenericUserException {
        DenodoResponse response  = new DenodoResponse();
        DenodoElement element = new DenodoElement();
        element.setVendorType("MFG");
        element.setStatus("Active");
        response.setElements(List.of(element));
        Mockito.lenient().when(client.get(anyString(), any())).thenReturn(response);

        final Step step = smeeUserVendorRelationStep.execute(context);
        assertThat(step, is(smeeUserEntityMergeStep));
    }

    @Test
    void execute_shouldThrowBadExceptionIfVendorTypeNotMFG() throws GenericUserException {
        DenodoResponse response  = new DenodoResponse();
        DenodoElement element = new DenodoElement();
        element.setVendorType("ABC");
        element.setStatus("Active");
        response.setElements(List.of(element));
        Mockito.lenient().when(client.get(anyString(), any())).thenReturn(response);

        Assertions.assertThrows(GenericBadRequestException.class, () -> smeeUserVendorRelationStep.execute(context));

    }

    @Test
    void execute_shouldThrowBadExceptionIfVendorStatusIsNotActive() throws GenericUserException {
        DenodoResponse response  = new DenodoResponse();
        DenodoElement element = new DenodoElement();
        element.setVendorType("MFG");
        element.setStatus("Deactivated");
        response.setElements(List.of(element));
        Mockito.lenient().when(client.get(anyString(), any())).thenReturn(response);

        Assertions.assertThrows(GenericBadRequestException.class, () -> smeeUserVendorRelationStep.execute(context));

    }

    @Test
    void execute_shouldThrowAnExceptionForEmptyVendors() throws GenericUserException {
        Mockito.lenient().when(client.get(anyString(), any())).thenReturn(new DenodoResponse());
        Assertions.assertThrows(GenericBadRequestException.class, () -> smeeUserVendorRelationStep.execute(context));
    }

}
