package com.gap.sourcing.smee.dtos.resources;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.NotBlank;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmeeUserCreateResource implements Resource {

        @NotBlank
        private String userName;
        @NotBlank
        private String userEmail;
        @NotBlank
        private String userType;
        private Boolean isVendor;
        private String vendorPartyId;
        @NotBlank
        private String userId;
    }


