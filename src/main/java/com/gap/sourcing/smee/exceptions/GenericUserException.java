package com.gap.sourcing.smee.exceptions;

import com.gap.sourcing.smee.dtos.resources.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GenericUserException extends RuntimeException {
    private final Resource resource;
    private final String message;

    public GenericUserException(final Resource resource, final String message) {
        super(message);
        this.resource = resource;
        this.message = message;
    }
}
