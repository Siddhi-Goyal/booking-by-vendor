package com.gap.sourcing.smee.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmeeUserTypeResponse implements Response {

    private List<SmeeUserTypes> userTypes;

}
