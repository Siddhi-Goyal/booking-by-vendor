package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.enums.RequestAction;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.exceptions.GenericUnknownActionException;
import com.gap.sourcing.smee.steps.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class SmeeUserStepManagerTest {

    @Mock
    private Step smeeUserCreateValidationStep;

    @Mock
    private Step smeeUserGetDataStep;

    private SmeeUserStepManager smeeUserStepManager;

    @BeforeEach
    void init() {
        smeeUserStepManager = new SmeeUserStepManager(smeeUserCreateValidationStep,smeeUserGetDataStep);

    }

    @Test
    void getFirstStep_shouldReturnUserCreateValidationStepGivenActionGet() throws GenericUserException {
        final Step step = smeeUserStepManager.getFirstStep(RequestAction.CREATE);
        assertThat(step, is(smeeUserCreateValidationStep));
    }

    @Test
    void getFirstStep_shouldThrowGenericUnknownActionExceptionGivenInvalidAction() {
        assertThrows(GenericUnknownActionException.class,
                () -> smeeUserStepManager.getFirstStep(RequestAction.DEFAULT)
        );
    }
}
