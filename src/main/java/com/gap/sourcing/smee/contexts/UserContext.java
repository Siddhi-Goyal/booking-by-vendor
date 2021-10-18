package com.gap.sourcing.smee.contexts;

import com.gap.sourcing.smee.dtos.resources.Resource;
import com.gap.sourcing.smee.dtos.responses.UserResponse;
import com.gap.sourcing.smee.entities.SmeeUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserContext implements Context {

    private Resource resource;
    private SmeeUser input;
    private SmeeUser current;
    private SmeeUser output;
    private UserResponse response;


    public UserContext(Resource resource) {
        this.resource = resource;

    }
}
