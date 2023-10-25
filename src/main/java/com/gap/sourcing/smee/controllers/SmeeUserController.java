package com.gap.sourcing.smee.controllers;

import brave.Tracer;
import com.gap.sourcing.smee.dtos.resources.SmeeUserGetResource;
import com.gap.sourcing.smee.dtos.responses.Response;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.dtos.responses.SmeeUserTypeResponse;
import com.gap.sourcing.smee.envelopes.Envelope;
import com.gap.sourcing.smee.services.ControllerStepService;
import com.gap.sourcing.smee.enums.RequestAction;
import com.gap.sourcing.smee.utils.TraceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import com.gap.sourcing.smee.exceptions.GenericUserException;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class SmeeUserController {

    private final ControllerStepService userControllerStepService;

    private static final String RESOURCE = "resource";

    private final Tracer tracer;

    SmeeUserController(final ControllerStepService userControllerStepService, Tracer tracer) {
        this.userControllerStepService = userControllerStepService;
        this.tracer = tracer;
    }

    @PostMapping
    public ResponseEntity<Envelope> createOrUpdateUser(final @Valid @RequestBody SmeeUserCreateResource resource) throws GenericUserException {
        String traceId = TraceUtil.getTraceId(tracer);

        log.info("Received requested to create User", kv(RESOURCE, resource));

        final Response response = userControllerStepService.process(RequestAction.CREATE, resource);

        final Envelope envelope = new Envelope(HttpStatus.OK.value(), traceId, response);

        log.info("SMEE User created successfully", kv(RESOURCE, response));

        return new ResponseEntity<>(envelope, HttpStatus.OK);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<Envelope> getUser(final @Valid SmeeUserGetResource resource) throws GenericUserException {
        String traceId = TraceUtil.getTraceId(tracer);

        log.info("Received request to get user details for user id", kv(RESOURCE, resource));

        final Response response = userControllerStepService.process(RequestAction.GET, resource);

        final Envelope envelope = new Envelope(HttpStatus.OK.value(), traceId, response);

        log.info("User response returned successfully", kv(RESOURCE, response));
        return new ResponseEntity<>(envelope, HttpStatus.OK);

    }

    @GetMapping("/types")
    public ResponseEntity<Envelope> getUserTypes() throws GenericUserException {
        String traceId = TraceUtil.getTraceId(tracer);

        log.info("Received request to get user types");

        final SmeeUserTypeResponse response = (SmeeUserTypeResponse)userControllerStepService.process(RequestAction.GET_USER_TYPES, null);

        final Envelope envelope = new Envelope(HttpStatus.OK.value(), traceId, response.getUserTypes());

        log.info("User types response returned successfully", kv(RESOURCE, response));
        return new ResponseEntity<>(envelope, HttpStatus.OK);
    }

    @PatchMapping("/last-login-date/{userName}")
    public ResponseEntity<Void> updateUserLastLoginDate(final @Valid SmeeUserGetResource resource) {
        log.info("Received requested to patch SMEE User", kv(RESOURCE, resource));

        userControllerStepService.process(RequestAction.PATCH, resource);

        log.info("SMEE User patched successfully");

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
