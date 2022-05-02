package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.entities.SmeeUserType;
import com.gap.sourcing.smee.exceptions.GenericBadRequestException;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.repositories.SmeeUserTypeRepository;
import com.gap.sourcing.smee.steps.Step;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Arrays;

import static com.gap.sourcing.smee.utils.RequestIdGenerator.REQUEST_ID_KEY;
import static net.logstash.logback.argument.StructuredArguments.kv;

@Component
@Slf4j
public class SmeeUserCreateResourceConversionStep implements Step {

    private final Step smeeUserLoadDataStep;
    private final SmeeUserTypeRepository smeeUserTypeRepository;

    public SmeeUserCreateResourceConversionStep(Step smeeUserLoadDataStep ,
                                                SmeeUserTypeRepository smeeUserTypeRepository) {
        this.smeeUserLoadDataStep = smeeUserLoadDataStep;
        this.smeeUserTypeRepository = smeeUserTypeRepository;

    }

    @Override
    public Step execute(Context context) throws GenericUserException {
        SmeeUserContext userContext = (SmeeUserContext) context;
        SmeeUserCreateResource userResource = (SmeeUserCreateResource) userContext.getResource();
        SmeeUser smeeUser = new SmeeUser();
        log.info("Converting incoming resource to smeeUser, resource={}", userResource, kv(REQUEST_ID_KEY,
                MDC.get(REQUEST_ID_KEY)));
        try {
            smeeUser.setUserName(userResource.getUserName());
            smeeUser.setUserEmail(userResource.getUserEmail());
            smeeUser.setUserTypeId(fetchUserTypeIdFromDB(userResource.getUserType()));
            smeeUser.setIsActive(true);
            smeeUser.setIsVendor(userResource.getIsVendor());
            smeeUser.setCreatedBy(userResource.getUserId());
            ZonedDateTime currentTimestamp = ZonedDateTime.now();
            smeeUser.setCreatedDate(currentTimestamp);
            smeeUser.setLastModifiedBy(userResource.getUserId());
            smeeUser.setLastModifiedDate(currentTimestamp);
            userContext.setSmeeUserType(userResource.getUserType());
            userContext.setInput(smeeUser);
            log.info("Converted incoming resource to smee user and saved in context's input attribute.",
                    kv(REQUEST_ID_KEY, MDC.get(REQUEST_ID_KEY)));
        } catch (Exception exception) {
            log.error("Something went wrong!!", kv(REQUEST_ID_KEY, MDC.get(REQUEST_ID_KEY)),
                    kv("exception", exception));
            throw new GenericBadRequestException(userResource, "Exception while converting resource to input " +
                    "entity object withStackTrace - " + Arrays.toString(exception.getStackTrace()));
        }
        return smeeUserLoadDataStep;
    }

    private Long fetchUserTypeIdFromDB (String userType){
         SmeeUserType smeeUserType =  smeeUserTypeRepository.findSmeeUserTypeByUserType(userType);
         return smeeUserType.getId();
    }
}
