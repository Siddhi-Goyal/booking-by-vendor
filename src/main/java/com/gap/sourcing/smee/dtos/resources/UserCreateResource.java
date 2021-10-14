package com.gap.sourcing.smee.dtos.resources;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserCreateResource implements Resource {

        @NotBlank
        @NotNull
        private String userName;
        @NotNull
        @NotBlank
        private String userEmail;
        @NotNull
        private String userType;
        private Boolean isVendor;
        @NotNull
        private String vendorPartyId;
        @NotNull
        private String userId;
    }


