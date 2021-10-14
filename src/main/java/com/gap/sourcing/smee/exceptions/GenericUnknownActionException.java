package com.gap.sourcing.smee.exceptions;

import com.gap.sourcing.smee.enums.RequestAction;

public class GenericUnknownActionException extends GenericUserException {
    private final RequestAction action;

    public GenericUnknownActionException(final RequestAction action) {
        this.action = action;
    }

    @Override
    public String getMessage() {
        return String.format("No steps found for action: %s", action);
    }
}
