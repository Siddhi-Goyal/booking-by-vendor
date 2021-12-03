package com.gap.sourcing.smee.contexts;

import com.gap.sourcing.smee.dtos.resources.Resource;
import com.gap.sourcing.smee.dtos.responses.Response;
import com.gap.sourcing.smee.dtos.responses.SmeeUserResponse;
import com.gap.sourcing.smee.dtos.responses.SmeeUserTypeResponse;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.entities.SmeeUserType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SmeeUserContext implements Context {

    private Resource resource;
    private SmeeUser input;
    private SmeeUser current;
    private SmeeUser output;
    private Response response;
    private String smeeUserType;
    private List<SmeeUserType> userTypeOutput;
    private SmeeUserTypeResponse userTypeResponse;


    public SmeeUserContext(Resource resource) {
        this.resource = resource;
        this.response = new SmeeUserResponse();
        this.userTypeResponse = new SmeeUserTypeResponse();
    }
}
