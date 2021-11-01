package com.gap.sourcing.smee.dtos.resources;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmeeUserCreateResource implements Resource {

        @NotBlank
        @Size(min = 1, max = 20)
        private String userName;
        @Email
        @NotBlank
        @Size(min = 1, max = 50)
        private String userEmail;
        @NotBlank
        @Size(min = 1, max = 20)
        private String userType;
        @NotNull
        private Boolean isVendor;
        private String vendorPartyId;
        @NotBlank
        @Size(min = 1, max = 10)
        private String userId;
    }


