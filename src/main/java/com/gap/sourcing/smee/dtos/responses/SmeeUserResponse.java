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
    private String userType;
    private String lastName;
    private String userEmail;
    private Boolean isVendor;
    private String firstName;
    private String createdBy;
    private String lastModifiedBy;
    private List<SmeeVendor> vendors;
    private ZonedDateTime createdDate;
    private ZonedDateTime lastModifiedDate;
}
