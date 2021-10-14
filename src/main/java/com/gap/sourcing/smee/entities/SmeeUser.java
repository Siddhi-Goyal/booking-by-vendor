package com.gap.sourcing.smee.entities;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SMEE_USER_T")
public class SmeeUser {

    @Id
    @Type(type = "uuid-char")
    @Generated
    private Integer id;
    private String userName;
    private String userEmail;
    private Integer userTypeId;
    private Boolean isActive;
    private Boolean isVendor;
    private String createdBy;
    private ZonedDateTime createdDate;
    private String lastModifiedBy;
    private ZonedDateTime lastModifiedDate;
}
