package com.gap.sourcing.smee.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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
    private String userName;
    private String userEmail;
    @OneToOne
    @JoinColumn(name = "user_type_id")
    private SmeeUserType userTypeId;
    private Boolean isActive;
    private Boolean isVendor;
    private String createdBy;
    private ZonedDateTime createdDate;
    private String lastModifiedBy;
    private ZonedDateTime lastModifiedDate;

    @OneToMany(mappedBy = "userName", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL )
    private List<SmeeUserVendor> vendors;

}
