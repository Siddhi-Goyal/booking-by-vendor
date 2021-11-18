package com.gap.sourcing.smee.envelopes;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.gap.sourcing.smee.dtos.responses.Response;
import com.gap.sourcing.smee.dtos.responses.SmeeUserTypes;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Envelope {
    private final Integer httpStatus;
    private final String requestId;
    private Response resource;
    private String error;
    private List<SmeeUserTypes> resources;

    public Envelope(Integer httpStatus, String requestId, Response response) {
        this.httpStatus = httpStatus;
        this.requestId = requestId;
        this.resource = response;
    }

    public Envelope(Integer httpStatus, String requestId, List<SmeeUserTypes> resources) {
        this.httpStatus = httpStatus;
        this.requestId = requestId;
        this.resources = resources;
    }

    public Envelope(Integer httpStatus, String requestId, String error) {
        this.httpStatus = httpStatus;
        this.requestId = requestId;
        this.error = error;
    }
}
