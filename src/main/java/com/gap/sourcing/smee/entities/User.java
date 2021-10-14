package com.gap.sourcing.smee.entities;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {
    private String userName;
    private String userEmail;
    private String userType;
    private Boolean isVendor;
    private String vendorPartyId;
    private String userId;
}
