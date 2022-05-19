package com.gap.sourcing.smee.dtos.responses.bamboorose;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VendorResponse {

    private String requestId;
    private VendorResource resource;
}
