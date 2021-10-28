package com.gap.sourcing.smee.controllers;

import com.gap.sourcing.smee.dtos.resources.SmeeUserGetResource;
import com.gap.sourcing.smee.dtos.responses.Response;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.envelopes.Envelope;
import com.gap.sourcing.smee.services.ControllerStepService;
import com.gap.sourcing.smee.enums.RequestAction;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import com.gap.sourcing.smee.exceptions.GenericUserException;

import static com.gap.sourcing.smee.utils.RequestIdGenerator.REQUEST_ID_KEY;
import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class SmeeUserController {

    private final ControllerStepService userControllerStepService;

    SmeeUserController(final ControllerStepService userControllerStepService) {
        this.userControllerStepService = userControllerStepService;
    }

    @PostMapping
    public ResponseEntity<Envelope> createOrUpdateUser(final @Valid @RequestBody SmeeUserCreateResource resource) throws GenericUserException {

        final String requestId = MDC.get(REQUEST_ID_KEY);

        log.info("Received requested to create User", kv("resource", resource), kv(REQUEST_ID_KEY, requestId));

        final Response response = userControllerStepService.process(RequestAction.CREATE, resource);

        final Envelope envelope = new Envelope(HttpStatus.OK.value(), requestId, response);

        log.info("SMEE User created successfully", kv("response", response), kv(REQUEST_ID_KEY, requestId));

        return new ResponseEntity<>(envelope, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Envelope> getUser(final @Valid SmeeUserGetResource resource) throws GenericUserException {

        final String requestId = MDC.get(REQUEST_ID_KEY);
        log.info("Received request to get user details for user id", kv("resource", resource), kv(REQUEST_ID_KEY, requestId));

        final Response response = userControllerStepService.process(RequestAction.GET, resource);

        final Envelope envelope = new Envelope(HttpStatus.OK.value(), requestId, response);

        log.info("User response returned successfully", kv("response", response), kv(REQUEST_ID_KEY, requestId));

        return new ResponseEntity<>(envelope, HttpStatus.OK);
    }
}
