package org.securityapps.vehicleregistry.common.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse (HttpStatus status, String message ) {
        ErrorResponse response=new ErrorResponse(status.value(),message,status.getReasonPhrase(), Instant.now());
        return ResponseEntity.status(status).body(response);
    }
}
