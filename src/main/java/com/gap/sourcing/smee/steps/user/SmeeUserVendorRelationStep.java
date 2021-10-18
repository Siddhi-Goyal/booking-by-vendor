package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.UserContext;
import com.gap.sourcing.smee.dtos.responses.UserResponse;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.services.Client;
import com.gap.sourcing.smee.steps.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SmeeUserVendorRelationStep implements Step {

    private final Step smeeUserPersistStep;

    private final Client client;

    public SmeeUserVendorRelationStep(Step smeeUserPersistStep, Client client) {
        this.smeeUserPersistStep = smeeUserPersistStep;
        this.client = client;
    }


    @Override
    public Step execute(Context context) throws GenericUserException {

        UserContext userContext = (UserContext) context;
        SmeeUser smeeUser = userContext.getCurrent();
        String denodoApi = "/server/sourcing_db/vendor_relationship/views/vendor_relationship";

     String gotJson =  client.get(denodoApi);

     System.out.println("================================gotJson"+gotJson);

        //Get the Vendor details from
       // fetchingVendors.getCurrentPartyId();

        return smeeUserPersistStep;
    }
}
