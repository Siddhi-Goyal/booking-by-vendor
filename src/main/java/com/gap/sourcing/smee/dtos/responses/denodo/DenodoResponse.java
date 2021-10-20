package com.gap.sourcing.smee.dtos.responses.denodo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class DenodoResponse {

    private String name;
    private List<DenodoElement> elements;
}
