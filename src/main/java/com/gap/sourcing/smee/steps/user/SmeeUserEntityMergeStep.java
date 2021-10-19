package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.steps.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SmeeUserEntityMergeStep implements Step {

    private final  Step smeeUserPersistStep;

    public SmeeUserEntityMergeStep(Step smeeUserPersistStep) {
        this.smeeUserPersistStep = smeeUserPersistStep;
    }

    @Override
    public Step execute(Context context) throws GenericUserException {
        SmeeUserContext smeeUserContext = (SmeeUserContext) context;
        SmeeUser input = smeeUserContext.getInput();
        SmeeUser current = smeeUserContext.getCurrent();

        log.info("Merging record's created details from context's current to context's input attribute, " +
                "input={}, current={}", input, current);
        if (current != null) {
            current.setLastModifiedBy(input.getLastModifiedBy());
            current.setLastModifiedDate(input.getLastModifiedDate());
            //TODO  handle vendors...
        }

        log.info("Merged record's created details from context's current to context's input attribute");

        return smeeUserPersistStep;
    }
}
