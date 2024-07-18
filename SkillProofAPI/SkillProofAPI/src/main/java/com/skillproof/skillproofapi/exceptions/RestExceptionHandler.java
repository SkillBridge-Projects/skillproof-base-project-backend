package com.skillproof.skillproofapi.exceptions;

import com.skillproof.skillproofapi.exceptions.apiError.ApiError;
import com.skillproof.skillproofapi.exceptions.apiError.SubError;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

    private static final String VALIDATION_ERROR = "Validation Error";

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NotNull HttpHeaders httpHeaders,
                                                                  @NotNull HttpStatus httpStatus,
                                                                  @NotNull WebRequest webRequest) {
        List<SubError> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            SubError subError = new SubError(error.getObjectName(), error.getField(), error.getDefaultMessage());
            errors.add(subError);
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            SubError subError = new SubError(error.getObjectName(), null, error.getDefaultMessage());
            errors.add(subError);
        }
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, VALIDATION_ERROR, errors);
        return handleExceptionInternal(ex, apiError, httpHeaders, apiError.getStatus(), webRequest);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFound(@NonNull final ResourceNotFoundException ex){
        SubError subError = getSubError(ex.getMessage(), ex.getObjectName(), ex.getFieldName());
        return buildResponseEntityApiError(new ApiError(HttpStatus.NOT_FOUND, VALIDATION_ERROR,
                Collections.singletonList(subError)));
    }

    @ExceptionHandler(ResourceFoundException.class)
    protected ResponseEntity<Object> handleResourceFound(@NonNull final ResourceFoundException ex){
        SubError subError = getSubError(ex.getMessage(), ex.getObjectName(), ex.getFieldName());
        return buildResponseEntityApiError(new ApiError(HttpStatus.BAD_REQUEST, VALIDATION_ERROR,
                Collections.singletonList(subError)));
    }

    @ExceptionHandler(InvalidRequestException.class)
    protected ResponseEntity<Object> handleInvalidRequestException(@NonNull final InvalidRequestException ex){
        return buildResponseEntityApiError(new ApiError(HttpStatus.BAD_REQUEST, VALIDATION_ERROR,
                Collections.singletonList(getSubError(ex.getMessage()))));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllException(@NonNull final Exception ex){
        return buildResponseEntityApiError(new ApiError(HttpStatus.BAD_REQUEST, VALIDATION_ERROR,
                Collections.singletonList(getSubError(ex.getMessage()))));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(@NonNull final IllegalArgumentException ex){
        return buildResponseEntityApiError(new ApiError(HttpStatus.BAD_REQUEST, VALIDATION_ERROR,
                Collections.singletonList(getSubError(ex.getMessage()))));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolationException(@NonNull final DataIntegrityViolationException ex){
        return buildResponseEntityApiError(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Database Error",
                ex.getCause()));
    }

    private SubError getSubError(String errorMessage) {
        return getSubError(errorMessage, null, null);
    }

    private SubError getSubError(String errorMessage, String objectName, String fieldName) {
        SubError.SubErrorBuilder builder = SubError.builder().message(errorMessage);
        if (StringUtils.isNotEmpty(objectName)) {
            builder.object(objectName);
        }
        if (StringUtils.isNotEmpty(fieldName)) {
            builder.field(fieldName);
        }
        return builder.build();
    }

    private ResponseEntity<Object> buildResponseEntityApiError(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
