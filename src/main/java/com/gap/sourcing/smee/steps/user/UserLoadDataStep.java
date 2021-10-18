package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.repositories.SmeeUserRepository;
import com.gap.sourcing.smee.steps.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserLoadDataStep implements Step {

    private final Step userVendorRelationStep;
    private final SmeeUserRepository smeeUserRepository;

    public UserLoadDataStep(final Step userVendorRelationStep, final SmeeUserRepository smeeUserRepository) {
        this.smeeUserRepository = smeeUserRepository;
        this.userVendorRelationStep = userVendorRelationStep;
    }

    @Override
    public Step execute(Context context) throws GenericUserException {

        return userVendorRelationStep;
    }
}
