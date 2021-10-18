package com.gap.sourcing.smee.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GenericUserException extends RuntimeException {

    public GenericUserException() {
    }

    public GenericUserException(String message) {
        super(message);
    }

    public GenericUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
