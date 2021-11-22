package com.gap.sourcing.smee.steps.user.types;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.entities.SmeeUserType;
import com.gap.sourcing.smee.exceptions.GenericBadRequestException;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.steps.Step;
import com.gap.sourcing.smee.steps.helper.SmeeUserTypeEntityToDTOConverter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.gap.sourcing.smee.utils.RequestIdGenerator.REQUEST_ID_KEY;
import static net.logstash.logback.argument.StructuredArguments.kv;


@Slf4j
@Component
public class SmeeUserTypeResponseConversionStep implements Step {

    private final SmeeUserTypeEntityToDTOConverter smeeUserTypeEntityToDTOConverter;

    public SmeeUserTypeResponseConversionStep(SmeeUserTypeEntityToDTOConverter smeeUserTypeEntityToDTOConverter) {
        this.smeeUserTypeEntityToDTOConverter = smeeUserTypeEntityToDTOConverter;
    }

    @Override
    public Step execute(Context context) throws GenericUserException {
        log.info("Converting context's output(smeeUserType) to response object.", kv(REQUEST_ID_KEY, MDC.get(REQUEST_ID_KEY)));

        try {
            SmeeUserContext smeeUserContext = (SmeeUserContext) context;
            List<SmeeUserType> entity = smeeUserContext.getUserTypeOutput();
            smeeUserContext.setUserTypeResponse(smeeUserTypeEntityToDTOConverter.convert(entity));
        } catch (Exception exception) {
            throw new GenericUserException("Exception in parsing data from DB to Response");
        }
        return null;
    }
}
