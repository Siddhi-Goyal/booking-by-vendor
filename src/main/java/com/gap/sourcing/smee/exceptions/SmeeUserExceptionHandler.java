package com.gap.sourcing.smee.exceptions;

import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.envelopes.Envelope;
import com.gap.sourcing.smee.utils.RequestIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransientPropertyValueException;
import org.slf4j.MDC;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gap.sourcing.smee.utils.RequestIdGenerator.REQUEST_ID_KEY;
import static net.logstash.logback.argument.StructuredArguments.kv;

@ControllerAdvice
@Slf4j
public class SmeeUserExceptionHandler {

    private static final String SMEE_USER_MISSING_PARAM_MESSAGE = "Smee user creation requires username, useremail, " +
            "usertype, isVendor, vendorPartyId and userid";
    private static final String MALFORMED_JSON_MESSAGE = "Bad Request - Passed Malformed JSON";
    private static final String INVALID_MESSAGE = "Bad Request - Malformed JSON,";
    private static final String VENDOR_CREATE_ERROR = "Something  went  wrong";
    private static final String REQUIRED_USER_ID = "Required userId";


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

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    protected ResponseEntity<Envelope> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        return handle(ex, REQUIRED_USER_ID, HttpStatus.METHOD_NOT_ALLOWED);

    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    protected ResponseEntity<Envelope> handleMalformedRequestException(HttpMessageNotReadableException ex, WebRequest request) {
        String responseMessage = getMessageForHttpMessageNotReadableException(ex);
        return handle(ex, responseMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Envelope> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        return handleRequestParamsInvalidException(ex);
    }

    @ExceptionHandler(value = {DataAccessResourceFailureException.class})
    protected ResponseEntity<Envelope> handleConstraintViolationException(DataAccessResourceFailureException ex, WebRequest request) {
        return handle(ex, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<Envelope> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return handle(ex, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {TransientPropertyValueException.class})
    protected ResponseEntity<Envelope> handleTransientPropertyValueException(TransientPropertyValueException ex, WebRequest request) {
        return handle(ex, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {CannotCreateTransactionException.class})
    protected ResponseEntity<Envelope> handleCannotCreateTransactionException(CannotCreateTransactionException ex, WebRequest request) {
        return handle(ex, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {InvalidDataAccessApiUsageException.class})
    protected ResponseEntity<Envelope> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex, WebRequest request) {
        return handle(ex, VENDOR_CREATE_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {ApiClientException.class})
    protected ResponseEntity<Envelope> handleApiClientException(ApiClientException ex, WebRequest request) {
        return handle(ex, ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Envelope> handle(Exception ex, String responseMessage, HttpStatus responseStatus) {
        String requestId = MDC.get(RequestIdGenerator.REQUEST_ID_KEY);
        log.error(String.format("Error processing request with reason - %s", responseMessage)
                , kv("stack_trace", ex.getStackTrace()), kv(REQUEST_ID_KEY, MDC.get(REQUEST_ID_KEY)));
        return new ResponseEntity<>(new Envelope(responseStatus.value(), requestId, responseMessage), responseStatus);
    }

    private ResponseEntity<Envelope> handleRequestParamsInvalidException(ConstraintViolationException ex) {
        List<String> exceptionFields = ex.getConstraintViolations().stream()
                .map(x -> String.format("Passed %s : %s is invalid", x.getPropertyPath(), x.getMessage()))
                .collect(Collectors.toList());
        return handle(ex, String.format("Bad Request - %s", exceptionFields), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Envelope> handleFieldsOrPathVariableInvalidException(Exception ex, BindingResult bindingResult) {
        if (bindingResult.getTarget() instanceof SmeeUserCreateResource) {
            List<ObjectError> violations = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
            List<String> errors = violations.stream().map(this::prepareError).filter(Objects::nonNull).collect(Collectors.toList());
            if (errors.isEmpty()) {
                return handle(ex, SMEE_USER_MISSING_PARAM_MESSAGE, HttpStatus.BAD_REQUEST);
            } else  {
                return handle(ex, String.join(", ", errors) , HttpStatus.BAD_REQUEST);
            }
        }

        List<String> exceptionFields = bindingResult.getFieldErrors().stream()
                .map(x -> String.format("Passed %s : %s is invalid", x.getField(), x.getRejectedValue()))
                .collect(Collectors.toList());
        return handle(ex, String.format("Bad Request - %s", exceptionFields), HttpStatus.BAD_REQUEST);
    }

    private String prepareError(ObjectError objectError) {
        try {
            String error = "";
            Object[] arguments = objectError.getArguments();
            if (arguments != null && arguments.length > 0) {
                DefaultMessageSourceResolvable messageResolver = (DefaultMessageSourceResolvable) arguments[0];
                error = messageResolver.getDefaultMessage() + " - " + objectError.getDefaultMessage();
            }
            return error;
        } catch (Exception exception) {
            log.info("Exception while  constructing error message");
            return null;
        }
    }

    private String getMessageForHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        if (ex == null || ex.getMessage() == null) {
            return MALFORMED_JSON_MESSAGE;
        }
        return INVALID_MESSAGE;
    }




}

