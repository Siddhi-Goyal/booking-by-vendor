package com.gap.sourcing.smee.dtos.responses.bamboorose;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorTier {
    private String id;
    private String relationshipStatusDescription;
    private boolean parentVendor;
    private VendorTierLinks _links;
}
