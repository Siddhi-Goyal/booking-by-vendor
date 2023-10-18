package com.gap.sourcing.smee.controllers;

import brave.Span;
import brave.Tracer;
import brave.propagation.TraceContext;
import com.gap.sourcing.smee.dtos.resources.Resource;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.dtos.resources.SmeeUserGetResource;
import com.gap.sourcing.smee.dtos.responses.SmeeUserResponse;
import com.gap.sourcing.smee.dtos.responses.SmeeUserTypeResponse;
import com.gap.sourcing.smee.enums.RequestAction;
import com.gap.sourcing.smee.envelopes.Envelope;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.services.ControllerStepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SmeeUserControllerTest {

    private SmeeUserCreateResource createResource;
    private SmeeUserGetResource getResource;
    @Mock
    private ControllerStepService controllerStepService;
    @Mock
    private Tracer tracer;
    @InjectMocks
    private SmeeUserController sampleController;


    @BeforeEach
    void init() {
        createResource = new SmeeUserCreateResource();
        getResource = new SmeeUserGetResource();

        Span spanMock = mock(Span.class);
        Mockito.when(tracer.currentSpan()).thenReturn(spanMock);
        TraceContext context = TraceContext.newBuilder().traceId(1).spanId(2).shared(true).build();
        Mockito.when(tracer.currentSpan().context()).thenReturn(context);    }

    @Test
    void createSmeeUser_ShouldReturnAStatusOkResponseEntity() throws GenericUserException {
        ResponseEntity<Envelope> response = sampleController.createOrUpdateUser(createResource);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void getSmeeUser_ShouldReturnAResponseEntityContainingAUserEnvelope() throws GenericUserException {


        final SmeeUserResponse expectedResponse = new SmeeUserResponse();
        when(controllerStepService.process(eq(RequestAction.GET), any(Resource.class))).thenReturn(expectedResponse);

        final ResponseEntity<Envelope> response = sampleController.getUser(getResource);

        assertThat(response.getStatusCode(), is(notNullValue()));
        assertThat(response.getBody(), is(notNullValue()));
    }

    @Test
    void getSmeeUserType_ShouldReturnAResponseEntityContainingAUserTypeEnvelope() throws GenericUserException {

        final SmeeUserTypeResponse expectedResponse = new SmeeUserTypeResponse();
        when(controllerStepService.process(eq(RequestAction.GET_USER_TYPES), eq(null))).thenReturn(expectedResponse);

        final ResponseEntity<Envelope> response = sampleController.getUserTypes();

        assertThat(response.getStatusCode(), is(notNullValue()));
        assertThat(response.getBody(), is(notNullValue()));
    }

    @Test
    void patchSmeeUser_ShouldReturnAStatusOkResponseEntity() throws GenericUserException {

        assertThat( sampleController.updateUserLastLoginDate(getResource).getStatusCode(),is(HttpStatus.OK));
    }

}
