package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.entities.SmeeUserType;
import com.gap.sourcing.smee.exceptions.GenericBadRequestException;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.repositories.SmeeUserTypeRepository;
import com.gap.sourcing.smee.steps.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import providers.ResourceProvider;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SmeeUserCreateValidationStepTest {

    @Mock
    SmeeUserCreateResourceConversionStep smeeUserCreateResourceConversionStep;

    @Mock
    SmeeUserTypeRepository smeeUserTypeRepository;

    private Step smeeUserCreateValidationStep;

    @BeforeEach
    void init() {
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
