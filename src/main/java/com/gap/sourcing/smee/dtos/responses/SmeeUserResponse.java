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
public class SmeeUserResponse implements Response{

    private Integer id;
    private String userName;
    private String userEmail;
    private String userType;
    private Boolean isVendor;
    private List<SmeeVendor> vendors;
    private String createdBy;
    private String lastModifiedBy;
    private ZonedDateTime createdDate;
    private ZonedDateTime lastModifiedDate;
}
