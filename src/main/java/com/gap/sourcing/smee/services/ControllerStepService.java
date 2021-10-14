package com.gap.sourcing.smee.services;

import com.gap.sourcing.smee.dtos.resources.Resource;
import com.gap.sourcing.smee.dtos.responses.Response;
import com.gap.sourcing.smee.enums.RequestAction;

import com.gap.sourcing.smee.exceptions.GenericUserException;

public interface ControllerStepService {
    Response process(RequestAction action, Resource resource) throws GenericUserException;
}
