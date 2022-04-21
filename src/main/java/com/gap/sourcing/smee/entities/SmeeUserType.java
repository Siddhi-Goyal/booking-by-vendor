package com.gap.sourcing.smee.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import org.hibernate.annotations.Type;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SMEE_USER_TYPE_T")
public class SmeeUserType {

    @Id
    @Type(type = "uuid-char")
    @GeneratedValue
    private UUID id;
    private String userType;
    private String description;
}

