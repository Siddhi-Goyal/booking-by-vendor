package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.repositories.SmeeUserRepository;
import com.gap.sourcing.smee.steps.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Component
public class SmeeUserLoadDataStep implements Step {

    private final Step smeeUserBuildVendorRelationStep;
    private final SmeeUserRepository smeeUserRepository;
    private final Step smeeUserEntityMergeStep;

    public SmeeUserLoadDataStep(final Step smeeUserBuildVendorRelationStep,
                                final SmeeUserRepository smeeUserRepository, Step smeeUserEntityMergeStep) {
        this.smeeUserRepository = smeeUserRepository;
        this.smeeUserBuildVendorRelationStep = smeeUserBuildVendorRelationStep;
        this.smeeUserEntityMergeStep = smeeUserEntityMergeStep;
    }

    @Override
    @Transactional(readOnly = true)
    public Step execute(Context context) throws GenericUserException {

        SmeeUserContext userContext = (SmeeUserContext) context;
        SmeeUser smeeUser = userContext.getInput();
        log.info("Loading  data for smee user {}", smeeUser.getUserName(), kv("userName", smeeUser.getUserName()));
        SmeeUser smeeUserFromDb  = smeeUserRepository.findSmeeUserByUserName(smeeUser.getUserName());

        if(smeeUserFromDb == null){
            log.info("User not found in database with UserName={}", smeeUser.getUserName(),
                    kv("userName", smeeUser.getUserName()));
            smeeUser.setVendors(List.of());
        } else {
            userContext.setCurrent(smeeUserFromDb);
        }
        if (Boolean.TRUE.equals(smeeUser.getIsVendor())) {
            return smeeUserBuildVendorRelationStep;
        } else {
            return smeeUserEntityMergeStep;
        }
    }
}
