package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.entities.SmeeUserType;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.repositories.SmeeUserTypeRepository;
import com.gap.sourcing.smee.steps.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import providers.ResourceProvider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class SmeeUserEntityMergeStepTest {

    @Mock
    Step smeeUserPersistStep;

    private SmeeUserEntityMergeStep smeeUserEntityMergeStep;

    @BeforeEach
    void init() {
     //   smeeUserType = new SmeeUserType();
      //  smeeUserType.setId(1);
        smeeUserEntityMergeStep = new SmeeUserEntityMergeStep( smeeUserPersistStep);
    }

    @Test
    void execute_shouldReturnASmeeUserLoadDataStep() throws GenericUserException {
        final SmeeUserCreateResource resource = ResourceProvider.getSmeeUserCreateResource();
        final SmeeUserContext context = new SmeeUserContext(resource);

        final Step step = smeeUserEntityMergeStep.execute(context);

        assertThat(step, is(smeeUserPersistStep));
    }
}
