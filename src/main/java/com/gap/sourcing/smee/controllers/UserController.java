package com.gap.sourcing.smee.controllers;

import com.gap.sourcing.smee.dtos.responses.Response;
import com.gap.sourcing.smee.dtos.resources.UserCreateResource;
import com.gap.sourcing.smee.services.ControllerStepService;
import com.gap.sourcing.smee.services.UserControllerStepService;
import com.gap.sourcing.smee.utils.RequestIdGenerator;
import com.gap.sourcing.smee.enums.RequestAction;
import com.gap.sourcing.smee.envelopes.Envelope;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gap.sourcing.smee.exceptions.GenericUserException;

import java.time.Instant;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final ControllerStepService userControllerStepService;

    UserController(final ControllerStepService userControllerStepService) {
        this.userControllerStepService = userControllerStepService;
    }

    @PostMapping
    public ResponseEntity<String> createOrUpdateUser(final @Valid @RequestBody UserCreateResource resource) throws GenericUserException {
        log.info("Received requested to create User", kv("resource", resource));

        final String requestId = MDC.get(RequestIdGenerator.REQUEST_ID_KEY);

        final Response response = userControllerStepService.process(RequestAction.CREATE, resource);

        return ResponseEntity.ok("Hello!!!, Your Request ID is: " + requestId);

    }
}
