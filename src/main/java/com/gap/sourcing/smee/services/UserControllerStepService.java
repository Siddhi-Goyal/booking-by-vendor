package com.gap.sourcing.smee.services;

import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.Resource;
import com.gap.sourcing.smee.dtos.responses.Response;
import com.gap.sourcing.smee.enums.RequestAction;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.steps.Step;
import com.gap.sourcing.smee.steps.StepManager;
import org.springframework.stereotype.Service;

@Service
public class UserControllerStepService implements ControllerStepService   {

    private final StepManager userStepManager;

    public UserControllerStepService(StepManager userStepManager) {
        this.userStepManager = userStepManager;
    }

    @Override
    public Response process(RequestAction action, Resource resource) throws GenericUserException {

        SmeeUserContext userContext = new SmeeUserContext(resource);
        Step nextStep = userStepManager.getFirstStep(action);

        while (nextStep != null) {
            nextStep = nextStep.execute(userContext);
        }
        if(resource == null){
            return userContext.getUserTypeResponse();
        }else{
            return userContext.getResponse();
        }
    }
}
