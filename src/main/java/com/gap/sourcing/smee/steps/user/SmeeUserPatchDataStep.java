package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserGetResource;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.repositories.SmeeUserRepository;
import com.gap.sourcing.smee.steps.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Slf4j
@Component
public class SmeeUserPatchDataStep implements Step {

    private final SmeeUserRepository smeeUserRepository;

    public SmeeUserPatchDataStep(SmeeUserRepository smeeUserRepository) {
        this.smeeUserRepository = smeeUserRepository;

    }

    @Override
    public Step execute(Context context) throws GenericUserException {
        SmeeUserContext userContext = (SmeeUserContext) context;
        SmeeUserGetResource resource = (SmeeUserGetResource) userContext.getResource();
        String userName = resource.getUserName();
        log.info("Getting user details for user-id, resource={}", resource);
        SmeeUser smeeUser = smeeUserRepository.findSmeeUserByUserName(userName);
        if (smeeUser == null) {
            userContext.setOutput(null);
        } else {
            smeeUser.setLastLoginDate(ZonedDateTime.now());
             smeeUserRepository.save(smeeUser);
        }
        return null;
    }

}
