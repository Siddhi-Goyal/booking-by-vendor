package com.gap.sourcing.smee.steps.user.types;

import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.entities.SmeeUserType;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.services.SmeeUserTypeLoadService;
import com.gap.sourcing.smee.steps.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SmeeUserTypeLoadDataStepTest {

    @Mock
    Step smeeUserTypeResponseConversionStep;

    @Mock
    SmeeUserTypeLoadService smeeUserTypeLoadService;

    private SmeeUserTypeLoadDataStep smeeUserTypeLoadDataStep;

    private SmeeUserContext context;
    List<SmeeUserType> entity;

    @BeforeEach
    void init() {

        context = new SmeeUserContext(null);
        entity = new ArrayList<SmeeUserType>();
        SmeeUserType sut = new SmeeUserType();
        sut.setUserType("GIS PD");
        sut.setDescription("Test");
        entity.add(sut);

        context.setUserTypeOutput(entity);
        smeeUserTypeLoadDataStep = new SmeeUserTypeLoadDataStep(smeeUserTypeLoadService,
                smeeUserTypeResponseConversionStep);
    }


    @Test
    void execute_shouldReturnASmeeUserType() throws GenericUserException {
        when(smeeUserTypeLoadService.getSmeeUserTypes()).thenReturn(context.getUserTypeOutput());
        final Step step = smeeUserTypeLoadDataStep.execute(context);

        assertThat(step, is(smeeUserTypeResponseConversionStep));
    }
}
