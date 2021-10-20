package com.gap.sourcing.smee.services;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.Resource;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.dtos.responses.Response;
import com.gap.sourcing.smee.dtos.responses.SmeeUserResponse;
import com.gap.sourcing.smee.entities.SmeeUserType;
import com.gap.sourcing.smee.enums.RequestAction;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.steps.StepManager;
import com.gap.sourcing.smee.steps.user.SmeeUserCreateValidationStep;
import com.gap.sourcing.smee.steps.user.SmeeUserStepManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import providers.ResourceProvider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerStepServiceTest {

   /* @Test
    void process_shouldReturnASmeeUserResponse() throws GenericUserException {

        final SmeeUserCreateResource resource = ResourceProvider.getSmeeUserCreateResource();
        final StepManager stepManager = mock(SmeeUserStepManager.class);
        final ControllerStepService controllerStepService = new UserControllerStepService(stepManager);
        //SmeeUserCreateResource resource = new SmeeUserCreateResource();
      //  resource.setUserName("abc");
        SmeeUserContext context = new SmeeUserContext(resource);
        SmeeUserResponse smeeUserResponse = new SmeeUserResponse();
        smeeUserResponse.setUserName("abc");
        context.setResponse(smeeUserResponse);
        final Response response    =    controllerStepService.process(RequestAction.CREATE,resource);
        assertThat(response, is(notNullValue()));
    }*/
}
