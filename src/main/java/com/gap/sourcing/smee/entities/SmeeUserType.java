package com.gap.sourcing.smee.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SMEE_USER_TYPE_T")
public class SmeeUserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userType;
    private String description;
}

