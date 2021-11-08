package com.gap.sourcing.smee.contexts;

import com.gap.sourcing.smee.dtos.resources.Resource;
import com.gap.sourcing.smee.dtos.responses.SmeeUserResponse;
import com.gap.sourcing.smee.entities.SmeeUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmeeUserContext implements Context {

    private Resource resource;
    private SmeeUser input;
    private SmeeUser current;
    private SmeeUser output;
    private SmeeUserResponse response;
    private String smeeUserType;


    public SmeeUserContext(Resource resource) {
        this.resource = resource;
        this.response = new SmeeUserResponse();
    }
}
