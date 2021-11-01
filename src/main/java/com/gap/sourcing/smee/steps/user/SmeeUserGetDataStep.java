package com.gap.sourcing.smee.steps.user;


import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserGetResource;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.entities.SmeeUserType;
import com.gap.sourcing.smee.exceptions.GenericBadRequestException;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.repositories.SmeeUserRepository;
import com.gap.sourcing.smee.repositories.SmeeUserTypeRepository;
import com.gap.sourcing.smee.steps.Step;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;

import static com.gap.sourcing.smee.utils.RequestIdGenerator.REQUEST_ID_KEY;
import static net.logstash.logback.argument.StructuredArguments.kv;

@Component
@Slf4j
public class SmeeUserGetDataStep implements Step {

    private final Step smeeUserResponseConversionStep;
    private final SmeeUserRepository smeeUserRepository;
    private final SmeeUserTypeRepository smeeUserTypeRepository;
    private static final String INVALID_USER_EMAIL_ERROR_MESSAGE = "User email is invalid";


    public SmeeUserGetDataStep(Step smeeUserResponseConversionStep, SmeeUserRepository smeeUserRepository,
                               SmeeUserTypeRepository smeeUserTypeRepository) {
        this.smeeUserRepository = smeeUserRepository;
        this.smeeUserResponseConversionStep = smeeUserResponseConversionStep;
        this.smeeUserTypeRepository = smeeUserTypeRepository;
    }

    public Step execute(Context context) throws GenericUserException {
        SmeeUserContext userContext = (SmeeUserContext) context;
        SmeeUserGetResource resource = (SmeeUserGetResource) userContext.getResource();
        String userIdToGetDetails = resource.getUserId();
        Optional<SmeeUser> smeeUser;
        try {
            if (userIdToGetDetails.contains("@")) {
            /*if(!validateUserEmail(userIdToGetDetails)){
                log.info(INVALID_USER_EMAIL_ERROR_MESSAGE,
                        kv("userId", resource.getUserId()), kv(REQUEST_ID_KEY, MDC.get(REQUEST_ID_KEY)));
                throw new GenericBadRequestException(resource, INVALID_USER_EMAIL_ERROR_MESSAGE);
            }*/
                smeeUser = Optional.ofNullable(smeeUserRepository.findSmeeUserByUserEmail(userIdToGetDetails));
            } else
                smeeUser = Optional.ofNullable(smeeUserRepository.findSmeeUserByUserName(userIdToGetDetails));

            if (smeeUser.isPresent()) {
                Long userTypeId = smeeUser.get().getUserTypeId();
                Optional<SmeeUserType> smeeUserType = smeeUserTypeRepository.findById(userTypeId);
                String userType = smeeUserType.get().getUserType();
                userContext.setSmeeUserType(userType);
                ((SmeeUserContext) context).setOutput(smeeUser.get());
            } else {
                userContext.setOutput(null);
            }
        } catch (Exception exception) {
            throw new GenericBadRequestException(resource,"Exception while fetching the user details");
        }
        return smeeUserResponseConversionStep;
    }

   /* Boolean validateUserEmail(String userEmail){
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern)
                .matcher(userEmail)
                .matches();
    }*/
}
