package com.gap.sourcing.smee.steps.user.types;

import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.entities.SmeeUserType;
import com.gap.sourcing.smee.exceptions.GenericBadRequestException;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.steps.Step;
import com.gap.sourcing.smee.steps.helper.SmeeUserTypeEntityToDTOConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class SmeeUserTypeResponseConversionStepTest {

    @Mock
    SmeeUserTypeEntityToDTOConverter smeeUserTypeEntityToDTOConverter;

    private Step smeeUserTypeResponseConversionStep;

    @BeforeEach
    void init() {

        smeeUserTypeResponseConversionStep = new SmeeUserTypeResponseConversionStep(smeeUserTypeEntityToDTOConverter);
    }

    @Test
    void execute_shouldReturnResponseInContext() throws GenericUserException {
        final SmeeUserContext context = new SmeeUserContext(null);
        final Step step = smeeUserTypeResponseConversionStep.execute(context);
        assertThat(step, is(nullValue()));
    }

    @Test
    void execute_shouldReturnResponseInContext_throws_exception() throws GenericUserException {
        List<SmeeUserType> entity = new ArrayList<SmeeUserType>();
        SmeeUserType sut = new SmeeUserType();
        sut.setUserType("GIS PD");
        sut.setDescription("Test");
        entity.add(sut);
        final SmeeUserContext context = new SmeeUserContext(null);
        context.setUserTypeOutput(entity);
        when(smeeUserTypeEntityToDTOConverter.convert(any())).thenThrow(GenericBadRequestException.class);
        Assertions.assertThrows(GenericUserException.class, () -> smeeUserTypeResponseConversionStep.execute(context));
    }

}
