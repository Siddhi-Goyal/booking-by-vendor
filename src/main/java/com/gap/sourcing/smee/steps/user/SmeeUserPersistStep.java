package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.repositories.SmeeUserRepository;
import com.gap.sourcing.smee.steps.Step;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import static com.gap.sourcing.smee.utils.RequestIdGenerator.REQUEST_ID_KEY;
import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Component
public class SmeeUserPersistStep implements Step {

    private final Step smeeUserResponseConversionStep;
    private final SmeeUserRepository smeeUserRepository;

    public SmeeUserPersistStep(Step smeeUserResponseConversionStep, SmeeUserRepository smeeUserRepository) {
        this.smeeUserRepository = smeeUserRepository;
        this.smeeUserResponseConversionStep = smeeUserResponseConversionStep;
    }

    @Override
    public Step execute(Context context) throws GenericUserException {
        final SmeeUserContext smeeUserContext = (SmeeUserContext) context;
        final SmeeUser input = smeeUserContext.getInput();
        final SmeeUser current = smeeUserContext.getCurrent();
        log.info("Persisting the context's input(smeeUser) attribute into database.",
                kv(REQUEST_ID_KEY, MDC.get(REQUEST_ID_KEY)));
        final SmeeUser output = smeeUserRepository.save(current ==  null  ? input : current);
        smeeUserContext.setOutput(output);

        log.info("Persisted context's input(smeeUser) attribute in database.",
                kv(REQUEST_ID_KEY, MDC.get(REQUEST_ID_KEY)));

        return smeeUserResponseConversionStep;
    }
}
