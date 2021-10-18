package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.UserContext;
import com.gap.sourcing.smee.dtos.resources.UserCreateResource;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.repositories.SmeeUserRepository;
import com.gap.sourcing.smee.steps.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Slf4j
@Component
public class SmeeUserLoadDataStep implements Step {

    private final Step smeeUserVendorRelationStep;
    private final SmeeUserRepository smeeUserRepository;

    public SmeeUserLoadDataStep(final Step smeeUserVendorRelationStep, final SmeeUserRepository smeeUserRepository) {
        this.smeeUserRepository = smeeUserRepository;
        this.smeeUserVendorRelationStep = smeeUserVendorRelationStep;
    }

    @Override
    public Step execute(Context context) throws GenericUserException {

        UserContext userContext = (UserContext) context;
        SmeeUser smeeUser = userContext.getInput();

        Optional<SmeeUser> optionalUserFromDB  = Optional.ofNullable(smeeUserRepository.findSmeeUserByUserName(smeeUser.getUserName()));

        if(optionalUserFromDB.isPresent()){

        }

        else {

            log.info("User not found in database, UserName={}", smeeUser.getUserName());
            userContext.setCurrent(smeeUser);

        }



        return smeeUserVendorRelationStep;
    }
}
