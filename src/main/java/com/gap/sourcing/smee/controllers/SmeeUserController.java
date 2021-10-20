package com.gap.sourcing.smee.controllers;

import com.gap.sourcing.smee.dtos.responses.Response;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.envelopes.Envelope;
import com.gap.sourcing.smee.services.ControllerStepService;
import com.gap.sourcing.smee.utils.RequestIdGenerator;
import com.gap.sourcing.smee.enums.RequestAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gap.sourcing.smee.exceptions.GenericUserException;

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
        log.info("Received requested to create User", kv("resource", resource));

        final String requestId = RequestIdGenerator.generateRequestId();

        final Response response = userControllerStepService.process(RequestAction.CREATE, resource);

        final Envelope envelope = new Envelope(HttpStatus.OK.value(), requestId, response);

        log.info("SMEE User created successfully", kv("response", response));

        return new ResponseEntity<>(envelope, HttpStatus.OK);
    }
}
