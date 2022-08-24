package com.gap.sourcing.smee.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmeeUserResponse implements Response {

    private String userName;
    private String firstName;
    private String lastName;
    private String userType;
    private String userEmail;
    private Boolean isVendor;
    private Boolean isAdmin;
    private Boolean isActive;
    private List<SmeeVendor> vendors;
    private String createdBy;
    private String lastModifiedBy;
    private ZonedDateTime createdDate;
    private ZonedDateTime lastModifiedDate;

}
