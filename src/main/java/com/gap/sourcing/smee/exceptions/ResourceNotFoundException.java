package com.gap.sourcing.smee.exceptions;

import com.gap.sourcing.smee.dtos.resources.Resource;

public class ResourceNotFoundException extends GenericUserException {
    private final Resource resource;
    private final String reason;

    public ResourceNotFoundException(final Resource resource, final String reason) {
        this.resource = resource;
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        return String.format("Bad Request - %s", reason);
    }
}
