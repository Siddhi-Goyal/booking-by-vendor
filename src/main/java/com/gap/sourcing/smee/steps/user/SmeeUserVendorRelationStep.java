package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.UserContext;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.steps.Step;
import com.gap.sourcing.smee.steps.helper.FetchingVendors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SmeeUserVendorRelationStep implements Step {

    private final Step smeeUserPersistStep;
    public FetchingVendors fetchingVendors;

    public SmeeUserVendorRelationStep(Step smeeUserPersistStep) {
        this.smeeUserPersistStep = smeeUserPersistStep;
    }


    @Override
    public Step execute(Context context) throws GenericUserException {

        UserContext userContext = (UserContext) context;
        SmeeUser smeeUser = userContext.getCurrent();

        //Get the Vendor details from
        fetchingVendors.getCurrentPartyId();


        return smeeUserPersistStep;
    }
}
