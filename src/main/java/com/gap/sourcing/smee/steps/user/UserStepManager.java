package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.enums.RequestAction;
import com.gap.sourcing.smee.exceptions.GenericUnknownActionException;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.steps.Step;
import com.gap.sourcing.smee.steps.StepManager;
import org.springframework.stereotype.Controller;

import java.util.EnumMap;
import java.util.Map;

@Controller
public class UserStepManager implements StepManager {

    private final Map<RequestAction, Step> firstStepMap;

    public UserStepManager( final Step UserCreateValidationStep) {
        this.firstStepMap = new EnumMap<>(RequestAction.class);

        firstStepMap.put(RequestAction.CREATE, UserCreateValidationStep);
    }

    @Override
    public Step getFirstStep(final RequestAction action) throws GenericUserException {
        final Step firstStep = firstStepMap.get(action);

        if (firstStep == null) {
            throw new GenericUnknownActionException(action);
        }

        return firstStep;
    }
}
