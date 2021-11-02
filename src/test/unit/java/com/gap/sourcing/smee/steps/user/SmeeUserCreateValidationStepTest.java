package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.dtos.responses.denodo.DenodoResponse;
import com.gap.sourcing.smee.entities.SmeeUserType;
import com.gap.sourcing.smee.enums.RequestAction;
import com.gap.sourcing.smee.exceptions.ApiClientException;
import com.gap.sourcing.smee.exceptions.GenericBadRequestException;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.exceptions.GenericUnknownActionException;
import com.gap.sourcing.smee.repositories.SmeeUserTypeRepository;
import com.gap.sourcing.smee.steps.Step;
import com.gap.sourcing.smee.utils.Client;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import providers.ResourceProvider;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SmeeUserCreateValidationStepTest {

    @Mock
    SmeeUserCreateResourceConversionStep smeeUserCreateResourceConversionStep;

    @Mock
    SmeeUserTypeRepository smeeUserTypeRepository;

    private SmeeUserType entity;
    private SmeeUserStepManager smeeUserStepManager;
    private Step smeeUserCreateValidationStep;

    @BeforeEach
    void init() {
        entity = new SmeeUserType();
        entity.setUserType("Garment Vendor");
        smeeUserCreateValidationStep = new SmeeUserCreateValidationStep(smeeUserCreateResourceConversionStep, smeeUserTypeRepository);
    }

    @Test
    void execute_shouldReturnASmeeUserCreateResourceConversionStepGivenAValidResource() throws GenericUserException {
        final SmeeUserCreateResource resource = ResourceProvider.getSmeeUserCreateResource();
        final SmeeUserContext context = new SmeeUserContext(resource);
        SmeeUserType smeeUserTypeGarmentVendor = SmeeUserType.builder().userType("Garment Vendor").build();
        SmeeUserType smeeUserTypeGisPD = SmeeUserType.builder().userType("GIS PD").build();
        List<SmeeUserType> smeeUserTypes = List.of(smeeUserTypeGarmentVendor, smeeUserTypeGisPD);
        when(smeeUserTypeRepository.findAll()).thenReturn(smeeUserTypes);

        final Step step = smeeUserCreateValidationStep.execute(context);

        assertThat(step, is(smeeUserCreateResourceConversionStep));
    }

    @Test
    void execute_shouldReturnASmeeUserCreateResourceConversionStepGivenAValidResource_emptydata() throws GenericUserException {
        final SmeeUserCreateResource resource = ResourceProvider.getSmeeUserCreateResource();
        final SmeeUserContext context = new SmeeUserContext(resource);
        when(smeeUserTypeRepository.findAll()).thenReturn(List.of());

        Assertions.assertThrows(GenericBadRequestException.class, () -> smeeUserCreateValidationStep.execute(context));
    }

    @Test
    void execute_shouldReturnASmeeUserCreateResourceConversionStepGivenAValidResource_vendor_without_vendor_party_id() throws GenericUserException {
        final SmeeUserCreateResource resource = ResourceProvider.getSmeeUserCreateResource();
        resource.setUserType("GIS PD");
        resource.setUserEmail("kausher@gap.com");
        resource.setVendorPartyId("");
        final SmeeUserContext context = new SmeeUserContext(resource);

        Assertions.assertThrows(GenericBadRequestException.class, () -> smeeUserCreateValidationStep.execute(context));
    }

    @Test
    void execute_shouldReturnASmeeUserCreateResourceConversionStepGivenAValidResource_vendor_without_vendor_type() throws GenericUserException {
        final SmeeUserCreateResource resource = ResourceProvider.getSmeeUserCreateResource();
        resource.setUserType("GIS PD");
        resource.setUserEmail("kausher@arvindexports.com");
        resource.setVendorPartyId("1004244");
        final SmeeUserContext context = new SmeeUserContext(resource);

        Assertions.assertThrows(GenericBadRequestException.class, () -> smeeUserCreateValidationStep.execute(context));
    }

    @Test
    void execute_shouldReturnASmeeUserCreateResourceConversionStepGivenAValidResource_not_vendor_with_vendor_type() throws GenericUserException {
        final SmeeUserCreateResource resource = ResourceProvider.getSmeeUserCreateResource();
        resource.setUserType("Garment Vendor");
        resource.setIsVendor(false);
        resource.setUserEmail("aravind@gap.com");
        final SmeeUserContext context = new SmeeUserContext(resource);

        Assertions.assertThrows(GenericBadRequestException.class, () -> smeeUserCreateValidationStep.execute(context));
    }

    @Test
    void execute_shouldReturnASmeeUserCreateResourceConversionStepGivenAValidResource_vendor_with_invalid_email() throws GenericUserException {
        final SmeeUserCreateResource resource = ResourceProvider.getSmeeUserCreateResource();
        resource.setUserType("Garment Vendor");
        resource.setIsVendor(false);
        resource.setUserEmail("kausher@arvindexports.com");
        final SmeeUserContext context = new SmeeUserContext(resource);

        Assertions.assertThrows(GenericBadRequestException.class, () -> smeeUserCreateValidationStep.execute(context));
    }
}
