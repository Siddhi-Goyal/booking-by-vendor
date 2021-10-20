package com.gap.sourcing.smee.services;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.Resource;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.dtos.responses.Response;
import com.gap.sourcing.smee.dtos.responses.SmeeUserResponse;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.entities.SmeeUserType;
import com.gap.sourcing.smee.enums.RequestAction;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.steps.Step;
import com.gap.sourcing.smee.steps.StepManager;
import com.gap.sourcing.smee.steps.user.SmeeUserCreateValidationStep;
import com.gap.sourcing.smee.steps.user.SmeeUserStepManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import providers.ResourceProvider;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerStepServiceTest {

    @Mock
    Step smeeUserResponseConversionStep;

    private ControllerStepService controllerStepService;

    @Mock
    SmeeUserStepManager userStepManager;
    @BeforeEach
    void init() {

         controllerStepService = new UserControllerStepService(userStepManager);
    }
/*
    @Test
    void process_shouldReturnASmeeUserResponse() throws GenericUserException {

        SmeeUserCreateResource resource = ResourceProvider.getSmeeUserCreateResource();
        //final StepManager userStepManager = mock(SmeeUserStepManager.class);

       *//* Context context ;
        SmeeUserContext smeeUserContext= new SmeeUserContext(resource);
        SmeeUserResponse smeeUserResponse = new SmeeUserResponse();
        smeeUserResponse.setUserName("abc");
*//*
        //when(userStepManager.getFirstStep(RequestAction.CREATE)).thenReturn(smeeUserResponseConversionStep);
         Response response    =    controllerStepService.process(RequestAction.CREATE,resource);
        assertThat(response, is(notNullValue()));
    }*/
}
