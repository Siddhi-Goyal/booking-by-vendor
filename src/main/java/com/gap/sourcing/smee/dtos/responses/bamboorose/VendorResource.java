package com.gap.sourcing.smee.dtos.responses.bamboorose;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VendorResource {

    private String id;
    private String legalName;
    private String type;
    private String status;
    private List<VendorTier> vendorTiers;


}
