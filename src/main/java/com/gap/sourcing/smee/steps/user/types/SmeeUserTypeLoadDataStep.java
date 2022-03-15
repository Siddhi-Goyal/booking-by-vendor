package com.gap.sourcing.smee.steps.user.types;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.entities.SmeeUserType;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.services.SmeeUserTypeLoadService;
import com.gap.sourcing.smee.steps.Step;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.gap.sourcing.smee.utils.RequestIdGenerator.REQUEST_ID_KEY;
import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Component
public class SmeeUserTypeLoadDataStep implements Step {

    private final SmeeUserTypeLoadService smeeUserTypeLoadService;
    private final Step smeeUserTypeResponseConversionStep;

    public SmeeUserTypeLoadDataStep(final SmeeUserTypeLoadService smeeUserTypeLoadService,
                                    Step smeeUserTypeResponseConversionStep) {
        this.smeeUserTypeResponseConversionStep = smeeUserTypeResponseConversionStep;
        this.smeeUserTypeLoadService = smeeUserTypeLoadService;
    }

    @Override
    public Step execute(Context context) throws GenericUserException {

        SmeeUserContext userContext = (SmeeUserContext) context;
        log.info("Loading  data for smee user types: ",kv(REQUEST_ID_KEY, MDC.get(REQUEST_ID_KEY)));
        List<SmeeUserType> smeeUserTypesFromDb  = smeeUserTypeLoadService.getSmeeUserTypes();
        if(smeeUserTypesFromDb == null){
            userContext.setUserTypeOutput(List.of());
        } else {
            userContext.setUserTypeOutput(smeeUserTypesFromDb);
        }
        return smeeUserTypeResponseConversionStep;
        }
    }

