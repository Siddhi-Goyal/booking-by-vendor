package com.gap.sourcing.smee.controllers;

import com.gap.sourcing.smee.utils.RequestIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping
    public ResponseEntity<String> sayHello() {
        String requestId = RequestIdGenerator.generateRequestId();
        log.info("Just to check logs", kv("requestId", requestId), kv("date", Instant.now().toString()));
        return ResponseEntity.ok("Hello!!!, Your Request ID is: " + requestId);
    }
}
