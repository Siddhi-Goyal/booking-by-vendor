package com.gap.sourcing.smee.controllers;

import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.envelopes.Envelope;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.services.ControllerStepService;
import com.gap.sourcing.smee.utils.RequestIdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(MockitoExtension.class)
public class SmeeUserControllerTest {

    private SmeeUserCreateResource createResource;
    @Mock
    private ControllerStepService controllerStepService;
    @InjectMocks
    private SmeeUserController sampleController;


    @BeforeEach
    void init() {
        createResource = new SmeeUserCreateResource();

        MDC.put(RequestIdGenerator.REQUEST_ID_KEY, RequestIdGenerator.generateRequestId());
    }

    @Test
    void createSmeeUser_ShouldReturnAStatusOkResponseEntity() throws GenericUserException {
        ResponseEntity<Envelope> response = sampleController.createOrUpdateUser(createResource);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }


}
