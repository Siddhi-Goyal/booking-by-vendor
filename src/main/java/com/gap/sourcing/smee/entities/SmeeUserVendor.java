package com.gap.sourcing.smee.entities;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SMEE_USER_VENDOR_T")
public class SmeeUserVendor {

    @Id
    @Generated
    private Integer id;
    private Integer userId;
    private String vendorPartyId;
    private String vendorName;
}
