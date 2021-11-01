package com.gap.sourcing.smee.envelopes;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.gap.sourcing.smee.dtos.responses.Response;
import lombok.Getter;

@Getter
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Envelope {
    private final Integer httpStatus;
    private final String requestId;
    private Response resource;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    public Envelope(Integer httpStatus, String requestId, Response response) {
        this.httpStatus = httpStatus;
        this.requestId = requestId;
        this.resource = response;
    }

    public Envelope(Integer httpStatus, String requestId, String error) {
        this.httpStatus = httpStatus;
        this.requestId = requestId;
        this.error = error;
    }
}
