package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.Resource;
import com.gap.sourcing.smee.dtos.responses.SmeeUserResponse;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.exceptions.GenericBadRequestException;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.steps.Step;
import com.gap.sourcing.smee.steps.helper.SmeeUserEntityToDTOConverter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import static com.gap.sourcing.smee.utils.RequestIdGenerator.REQUEST_ID_KEY;
import static net.logstash.logback.argument.StructuredArguments.kv;


@Slf4j
@Component
public class SmeeUserResponseConversionStep implements Step {

    private final SmeeUserEntityToDTOConverter smeeUserEntityToDTOConverter;

    public SmeeUserResponseConversionStep(SmeeUserEntityToDTOConverter smeeUserEntityToDTOConverter) {
        this.smeeUserEntityToDTOConverter = smeeUserEntityToDTOConverter;
    }

    @Override
    public Step execute(Context context) throws GenericUserException {
        log.info("Converting context's output(smeeUser) to response object.", kv(REQUEST_ID_KEY, MDC.get(REQUEST_ID_KEY)));
        Resource userResource = ((SmeeUserContext) context).getResource();
        SmeeUserResponse response = new SmeeUserResponse();
        try {
            SmeeUserContext smeeUserContext = (SmeeUserContext) context;
            SmeeUser entity = smeeUserContext.getOutput();
            if(entity!=null) {
                String userType = smeeUserContext.getSmeeUserType();
                 response = smeeUserEntityToDTOConverter.convert(entity, userType);
                smeeUserContext.setResponse(response);
            }
           else{
                smeeUserContext.setResponse(null);
            }


        } catch (Exception exception) {
            throw new GenericBadRequestException(userResource, "Exception in parsing data from DB to Response");
        }
        return null;
    }
}
