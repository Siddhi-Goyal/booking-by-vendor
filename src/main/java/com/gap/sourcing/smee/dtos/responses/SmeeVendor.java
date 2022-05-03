package com.gap.sourcing.smee.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class SmeeVendor {

    private UUID id;
    private String vendorPartyId;
    private String vendorName;
}
