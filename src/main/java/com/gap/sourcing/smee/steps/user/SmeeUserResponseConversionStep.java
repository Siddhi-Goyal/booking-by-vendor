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
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class SmeeUserResponseConversionStep implements Step {

    private final SmeeUserEntityToDTOConverter smeeUserEntityToDTOConverter;

    public SmeeUserResponseConversionStep(SmeeUserEntityToDTOConverter smeeUserEntityToDTOConverter) {
        this.smeeUserEntityToDTOConverter = smeeUserEntityToDTOConverter;
    }

    @Override
    public Step execute(Context context) throws GenericUserException {
        log.info("Converting context's output(smeeUser) to response object.");
        Resource resource = ((SmeeUserContext) context).getResource();
        try {
            SmeeUserContext smeeUserContext = (SmeeUserContext) context;
            SmeeUser entity = smeeUserContext.getOutput();
            SmeeUserResponse response = smeeUserEntityToDTOConverter.convert(entity);
            smeeUserContext.setResponse(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new GenericBadRequestException(resource, "Exception in parsing data from DB to Response " +
                    "withStackTrace - " + Arrays.toString(exception.getStackTrace()));
        }
        return null;
    }
}
