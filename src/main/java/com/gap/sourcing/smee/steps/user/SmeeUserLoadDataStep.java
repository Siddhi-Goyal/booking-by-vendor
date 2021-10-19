package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.repositories.SmeeUserRepository;
import com.gap.sourcing.smee.steps.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

        SmeeUserContext userContext = (SmeeUserContext) context;
        SmeeUser smeeUser = userContext.getInput();

        SmeeUser smeeUserFromDb  = smeeUserRepository.findSmeeUserByUserName(smeeUser.getUserName());

        if(smeeUserFromDb != null){
            userContext.setCurrent(smeeUserFromDb);
        } else {
            log.info("User not found in database, UserName={}", smeeUser.getUserName());
        }
        return smeeUserVendorRelationStep;
    }
}
