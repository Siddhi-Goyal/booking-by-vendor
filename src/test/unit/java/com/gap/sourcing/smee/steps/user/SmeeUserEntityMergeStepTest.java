package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.entities.SmeeUserVendor;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.steps.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import providers.ResourceProvider;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class SmeeUserEntityMergeStepTest {

    @Mock
    Step smeeUserPersistStep;

    private SmeeUserEntityMergeStep smeeUserEntityMergeStep;

    @BeforeEach
    void init() {
        smeeUserEntityMergeStep = new SmeeUserEntityMergeStep( smeeUserPersistStep);
    }

    @Test
    void execute_shouldReturnASmeeUserLoadDataStep() throws GenericUserException {
        final SmeeUserCreateResource resource = ResourceProvider.getSmeeUserCreateResource();
        final SmeeUserContext context = new SmeeUserContext(resource);

        final Step step = smeeUserEntityMergeStep.execute(context);

        assertThat(step, is(smeeUserPersistStep));
    }

    @Test
    void execute_shouldReturnASmeeUserLoadDataStep_current_not_null() throws GenericUserException {
        final SmeeUserCreateResource resource = ResourceProvider.getSmeeUserCreateResource();
        final SmeeUserContext context = new SmeeUserContext(resource);
        SmeeUserVendor vendor =  SmeeUserVendor.builder().vendorPartyId("12345").vendorName("Test Name").build();
        SmeeUserVendor vendor1 =  SmeeUserVendor.builder().vendorPartyId("908756").vendorName("Another Test Name").build();
        SmeeUserVendor vendor2 =  SmeeUserVendor.builder().vendorPartyId("786578").vendorName("New Test Name").build();
        SmeeUser input = SmeeUser.builder().lastModifiedDate(ZonedDateTime.now()).lastModifiedBy("syarram")
                .isVendor(true)
                .vendors(new ArrayList<>(Arrays.asList(vendor, vendor1))).build();
        SmeeUser current = SmeeUser.builder().lastModifiedDate(ZonedDateTime.now()).lastModifiedBy("syarram")
                .vendors(new ArrayList<>(Arrays.asList(vendor2, vendor1))).build();

        context.setCurrent(current);
        context.setInput(input);

        final Step step = smeeUserEntityMergeStep.execute(context);

        assertThat(step, is(smeeUserPersistStep));
    }
}
