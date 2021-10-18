package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.steps.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserVendorRelationStep implements Step {

    private final Step smeeUserPersistStep;

    public UserVendorRelationStep(Step smeeUserPersistStep) {
        this.smeeUserPersistStep = smeeUserPersistStep;
    }


    @Override
    public Step execute(Context context) throws GenericUserException {
        return smeeUserPersistStep;
    }
}
