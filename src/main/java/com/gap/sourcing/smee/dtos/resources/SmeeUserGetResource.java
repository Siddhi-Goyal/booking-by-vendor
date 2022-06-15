package com.gap.sourcing.smee.dtos.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmeeUserGetResource implements Resource {

    @NotBlank
    private  String userName;

}
