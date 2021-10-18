package com.gap.sourcing.smee.exceptions;

import com.gap.sourcing.smee.dtos.resources.UserCreateResource;
import com.gap.sourcing.smee.envelopes.Envelope;
import com.gap.sourcing.smee.utils.RequestIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.kv;

@ControllerAdvice
@Slf4j
public class SmeeUserExceptionHandler {

    private static final String SMEE_USER_MISSING_PARAM_MESSAGE = "Smee user creation requires username, useremail, " +
            "usertype, isZVendor, vendorPartyId and userid";
    private static final String MALFORMED_JSON_MESSAGE = "Bad Request - Passed Malformed JSON";
    private static final String INVALID_MESSAGE = "Bad Request - Malformed JSON,";


    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler(value = {GenericBadRequestException.class})
    protected ResponseEntity<Envelope> handleBadRequestException(GenericBadRequestException ex, WebRequest request) {
        return handle(ex, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler(value = {GenericUnknownActionException.class})
    protected ResponseEntity<Envelope> handleUnknownActionException(GenericUnknownActionException ex, WebRequest request) {
        return handle(ex, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<Envelope> handleFieldNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        return handleFieldsOrPathVariableInvalidException(ex, ex.getBindingResult());
    }

    @ExceptionHandler(value = {BindException.class})
    protected ResponseEntity<Envelope> handlePathVariableNotValidException(BindException ex, WebRequest request) {
        return handleFieldsOrPathVariableInvalidException(ex, ex.getBindingResult());
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    protected ResponseEntity<Envelope> handleMalformedRequestException(HttpMessageNotReadableException ex, WebRequest request) {
        String responseMessage = getMessageForHttpMessageNotReadableException(ex);
        return handle(ex, responseMessage, HttpStatus.BAD_REQUEST);
    }

    /*@ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<Envelope> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return handle(ex, ex.getMessage(), HttpStatus.NOT_FOUND);
    }*/

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Envelope> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        return handleRequestParamsInvalidException(ex);
    }

    @ExceptionHandler(value = {DataAccessResourceFailureException.class})
    protected ResponseEntity<Envelope> handleConstraintViolationException(DataAccessResourceFailureException ex, WebRequest request) {
        return handle(ex, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Envelope> handle(Exception ex, String responseMessage, HttpStatus responseStatus) {
        String requestId = MDC.get(RequestIdGenerator.REQUEST_ID_KEY);
        log.error(String.format("Error processing request with reason - %s", responseMessage)
                , kv("stack_trace", ex.getStackTrace()));
        return new ResponseEntity<>(new Envelope(responseStatus.value(), requestId, responseMessage), responseStatus);
    }

    private ResponseEntity<Envelope> handleRequestParamsInvalidException(ConstraintViolationException ex) {
        List<String> exceptionFields = ex.getConstraintViolations().stream()
                .map(x -> String.format("Passed %s : %s is invalid", x.getPropertyPath(), x.getMessage()))
                .collect(Collectors.toList());
        return handle(ex, String.format("Bad Request - %s", exceptionFields.toString()), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Envelope> handleFieldsOrPathVariableInvalidException(Exception ex, BindingResult bindingResult) {
        if (bindingResult.getTarget() instanceof UserCreateResource) {
            return handle(ex, SMEE_USER_MISSING_PARAM_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        List<String> exceptionFields = bindingResult.getFieldErrors().stream()
                .map(x -> String.format("Passed %s : %s is invalid", x.getField(), x.getRejectedValue()))
                .collect(Collectors.toList());
        return handle(ex, String.format("Bad Request - %s", exceptionFields.toString()), HttpStatus.BAD_REQUEST);
    }

    private String getMessageForHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        if (ex == null || ex.getMessage() == null) {
            return MALFORMED_JSON_MESSAGE;
        }
        return INVALID_MESSAGE;
    }
}

