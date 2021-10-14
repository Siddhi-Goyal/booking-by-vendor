package com.gap.sourcing.smee.services;

import com.gap.sourcing.smee.contexts.UserContext;
import com.gap.sourcing.smee.dtos.resources.Resource;
import com.gap.sourcing.smee.dtos.responses.Response;
import com.gap.sourcing.smee.enums.RequestAction;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.steps.Step;
import com.gap.sourcing.smee.steps.StepManager;

public class UserControllerStepService implements ControllerStepService   {

    private final StepManager userStepManager;

    public UserControllerStepService(StepManager userStepManager) {
        this.userStepManager = userStepManager;
    }

    @Override
    public Response process(RequestAction action, Resource resource) throws GenericUserException {
        UserContext userContext = new UserContext(resource);

        Step nextStep = userStepManager.getFirstStep(action);

        while (nextStep != null) {
            nextStep = nextStep.execute(userContext);
        }
      //  return action.equals(RequestAction.GET) ? vendorContext.getVendorSearchResponse() : vendorContext.getResponse();
        return (Response) userContext.getResponse();

    }

}
