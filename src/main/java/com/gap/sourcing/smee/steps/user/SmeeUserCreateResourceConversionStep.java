package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.UserContext;
import com.gap.sourcing.smee.dtos.resources.UserCreateResource;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.steps.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Arrays;

@Component
@Slf4j
public class SmeeUserCreateResourceConversionStep implements Step {

    private Step userLoadDataStep;

    public SmeeUserCreateResourceConversionStep(Step userLoadDataStep) {
        this.userLoadDataStep = userLoadDataStep;
    }

    @Override
    public Step execute(Context context) throws GenericUserException {
        UserContext userContext = (UserContext) context;
        UserCreateResource userResource = (UserCreateResource) userContext.getResource();
        SmeeUser smeeUser = new SmeeUser();
        log.info("Converting incoming resource to smeeUser, resource={}", userResource);
        try {
            smeeUser.setUserName(userResource.getUserName());
            smeeUser.setUserEmail(userResource.getUserEmail());
            smeeUser.setUserTypeId(1);//TODO fetch user type id from DB
            smeeUser.setIsActive(true);
            smeeUser.setIsVendor(userResource.getIsVendor());
            smeeUser.setCreatedBy(userResource.getUserId());//TODO check with managers
            ZonedDateTime currentTimestamp = ZonedDateTime.now();
            smeeUser.setCreatedDate(currentTimestamp);
            smeeUser.setLastModifiedBy(userResource.getUserId());
            smeeUser.setLastModifiedDate(currentTimestamp);

            userContext.setInput(smeeUser);
            log.info("Converted incoming resource to smee user and saved in context's input attribute.");
        } catch (Exception exception) {
            throw new GenericUserException(userResource, "Exception while converting resource to input entity object " +
                    "withStackTrace - " + Arrays.toString(exception.getStackTrace()));
        }
        return userLoadDataStep;
    }
}
