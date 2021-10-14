package com.gap.sourcing.smee.steps;

import com.gap.sourcing.smee.enums.RequestAction;
import com.gap.sourcing.smee.exceptions.GenericUserException;

public interface StepManager {
    Step getFirstStep(RequestAction action) throws GenericUserException;
}
