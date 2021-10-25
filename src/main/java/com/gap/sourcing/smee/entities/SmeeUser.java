package com.gap.sourcing.smee.entities;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SMEE_USER_T")
public class SmeeUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String userEmail;
    private Long userTypeId;
    private Boolean isActive;
    private Boolean isVendor;
    private String createdBy;
    private ZonedDateTime createdDate;
    private String lastModifiedBy;
    private ZonedDateTime lastModifiedDate;

    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL )
    private List<SmeeUserVendor> vendors;

}
