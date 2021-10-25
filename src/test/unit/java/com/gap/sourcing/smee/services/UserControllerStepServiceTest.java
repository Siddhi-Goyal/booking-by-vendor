package com.gap.sourcing.smee.services;

import com.gap.sourcing.smee.dtos.resources.Resource;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.dtos.responses.Response;
import com.gap.sourcing.smee.enums.RequestAction;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.steps.StepManager;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.mock;

class UserControllerStepServiceTest {
    @Test
    void process_shouldReturnASmeeUserResponse() throws GenericUserException {
        final StepManager stepManager = mock(StepManager.class);
        final ControllerStepService controllerStepService = new UserControllerStepService(stepManager);
        final Resource resource = SmeeUserCreateResource.builder().build();

        final Response response = controllerStepService.process(RequestAction.CREATE, resource);

        assertThat(response, is(notNullValue()));

    }
}
