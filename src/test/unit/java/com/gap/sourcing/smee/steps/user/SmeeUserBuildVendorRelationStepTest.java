package com.gap.sourcing.smee.steps.user;


import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.dtos.responses.bamboorose.VendorResource;
import com.gap.sourcing.smee.dtos.responses.bamboorose.VendorResponse;
import com.gap.sourcing.smee.dtos.responses.bamboorose.VendorTier;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
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
        smeeUserVendorRelationStep = new SmeeUserBuildVendorRelationStep(smeeUserEntityMergeStep, client);
        SmeeUserCreateResource resource = new SmeeUserCreateResource();
        resource.setVendorPartyId("12345678");
        context = new SmeeUserContext(resource);
        entity = new SmeeUser();
        entity.setUserName("xyz");
        entity.setUserEmail("xyz@abc.com");
        entity.setIsVendor(true);
        context.setInput(entity);
    }

    @Test
    void execute_shouldReturnASmeeUserEntityMergeStepStep() throws GenericUserException {

        VendorResponse firstVendorResponse = getFirstVendorResponse();
        VendorResponse secondVendorResponse = getSecondVendorResponse();
        VendorResource firstVendorResource = firstVendorResponse.getResource();
        VendorResource secondVendorResource = secondVendorResponse.getResource();
        String url = "some-url";
        ReflectionTestUtils.setField(smeeUserVendorRelationStep,"vendorProfileUri",url);
        Mockito.lenient().when(client.get(url+firstVendorResource.getId(), VendorResponse.class)).thenReturn(firstVendorResponse);
        Mockito.lenient().when(client.get(url+secondVendorResource.getId(), VendorResponse.class)).thenReturn(secondVendorResponse);
        final Step step = smeeUserVendorRelationStep.execute(context);
        assertThat(step, is(smeeUserEntityMergeStep));
    }

    @Test
    void execute_shouldThrowBadExceptionIfVendorTypeNotMFG() throws GenericUserException {
        VendorResponse response  = new VendorResponse();
        VendorResource vendorResource = new VendorResource();
        vendorResource.setType("ABC");
        vendorResource.setStatus("A");
        VendorTier vendorTier = new VendorTier();
        vendorResource.setVendorTiers(List.of(vendorTier));
        Mockito.lenient().when(client.get(anyString(), any())).thenReturn(response);
        Assertions.assertThrows(GenericBadRequestException.class, () -> smeeUserVendorRelationStep.execute(context));

    }

    @Test
    void execute_shouldThrowBadExceptionIfVendorStatusIsNotActive() throws GenericUserException {
        VendorResponse response  = new VendorResponse();
        VendorResource vendorResource = new VendorResource();
        vendorResource.setType("MFG");
        vendorResource.setStatus("DEACTIVATED");
        response.setResource(vendorResource);
        VendorTier vendorTier = new VendorTier();
        vendorResource.setVendorTiers(List.of(vendorTier));
        Mockito.lenient().when(client.get(anyString(), any())).thenReturn(response);
        Assertions.assertThrows(GenericBadRequestException.class, () -> smeeUserVendorRelationStep.execute(context));

    }

    @Test
    void execute_shouldThrowAnExceptionForEmptyVendors() throws GenericUserException {
        Mockito.lenient().when(client.get(anyString(), any())).thenReturn(new VendorResponse());
        Assertions.assertThrows(GenericBadRequestException.class, () -> smeeUserVendorRelationStep.execute(context));
    }

    @Test
    void execute_shouldThrowAnExceptionForNullVendorResponse() throws GenericUserException {
        Mockito.lenient().when(client.get(anyString(), any())).thenReturn(null);
        Assertions.assertThrows(GenericBadRequestException.class, () -> smeeUserVendorRelationStep.execute(context));
    }


    private VendorResponse getFirstVendorResponse(){
        VendorResponse response  = new VendorResponse();
        VendorResource vendorResource = new VendorResource();
        vendorResource.setStatus("A");
        vendorResource.setId("12345678");
        vendorResource.setType("MFG");
        response.setResource(vendorResource);
        VendorTier VendorTier = new VendorTier();
        VendorTier.setId("1000045");
        VendorTier.setRelationshipStatusDescription("ACTIVE");
        vendorResource.setVendorTiers(new ArrayList<>());
        vendorResource.getVendorTiers().add(VendorTier);
        return response;
    }

    private VendorResponse getSecondVendorResponse(){
        VendorResponse response  = new VendorResponse();
        VendorResource vendorResource = new VendorResource();
        vendorResource.setStatus("A");
        vendorResource.setId("1000045");
        vendorResource.setType("MFG");
        response.setResource(vendorResource);
        VendorTier VendorTier = new VendorTier();
        VendorTier.setId("1000046");
        VendorTier.setRelationshipStatusDescription("DEACTIVE");
        return response;
    }

}
