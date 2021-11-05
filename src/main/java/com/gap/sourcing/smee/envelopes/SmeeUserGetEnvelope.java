package com.gap.sourcing.smee.envelopes;

import com.gap.sourcing.smee.dtos.responses.Response;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter

public class SmeeUserGetEnvelope extends Envelope{

    private final List<?> resources;

    public SmeeUserGetEnvelope(Integer httpStatus, String requestId, List<?> resource) {
        super(httpStatus, requestId, (Response) null);
        this.resources = resource;
    }
}
