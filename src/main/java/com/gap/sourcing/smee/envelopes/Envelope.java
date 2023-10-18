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
    private final String traceId;
    private Response resource;
    private String error;
    private List<SmeeUserTypes> resources;

    public Envelope(Integer httpStatus, String traceId, Response response) {
        this.httpStatus = httpStatus;
        this.traceId = traceId;
        this.resource = response;
    }

    public Envelope(Integer httpStatus, String traceId, List<SmeeUserTypes> resources) {
        this.httpStatus = httpStatus;
        this.traceId = traceId;
        this.resources = resources;
    }

    public Envelope(Integer httpStatus, String traceId, String error) {
        this.httpStatus = httpStatus;
        this.traceId = traceId;
        this.error = error;
    }
}
