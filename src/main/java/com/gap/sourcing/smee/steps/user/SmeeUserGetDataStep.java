package com.gap.sourcing.smee.steps.user;


import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserGetResource;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.repositories.SmeeUserRepository;
import com.gap.sourcing.smee.services.SmeeUserTypeLoadService;
import com.gap.sourcing.smee.steps.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Slf4j
public class SmeeUserGetDataStep implements Step {

    private final Step smeeUserResponseConversionStep;
    private final SmeeUserRepository smeeUserRepository;
    private final SmeeUserTypeLoadService smeeUserTypeLoadService;

    public SmeeUserGetDataStep(Step smeeUserResponseConversionStep, SmeeUserRepository smeeUserRepository,
                               SmeeUserTypeLoadService smeeUserTypeLoadService) {
        this.smeeUserRepository = smeeUserRepository;
        this.smeeUserResponseConversionStep = smeeUserResponseConversionStep;
        this.smeeUserTypeLoadService = smeeUserTypeLoadService;
    }

    @Transactional(readOnly = true)
    public Step execute(Context context) throws GenericUserException {
        SmeeUserContext userContext = (SmeeUserContext) context;
        SmeeUserGetResource resource = (SmeeUserGetResource) userContext.getResource();
        String userIdToGetDetails = resource.getUserName();

        log.info("Getting user details for user-id, resource={}", resource);
        SmeeUser smeeUser = smeeUserRepository.findSmeeUserByUserName(userIdToGetDetails);
        
        if (smeeUser == null) {
            userContext.setOutput(null);
        } else {
            String smeeUserTypeVal = smeeUserTypeLoadService.fetchUserTypeFromCache(smeeUser.getUserTypeId().getId());
            userContext.setSmeeUserType(smeeUserTypeVal);
            ((SmeeUserContext) context).setOutput(smeeUser);
        }
        return smeeUserResponseConversionStep;
    }

}
