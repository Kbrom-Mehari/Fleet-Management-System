package org.securityapps.vehicleregistry.common.exception;

import lombok.Data;

import java.time.Instant;

@Data
public class ErrorResponse {
    private int statusCode;
    private String message;
    private String details;
    private Instant timestamp;

    public ErrorResponse(int statusCode, String message, String details, Instant timestamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
    }
}
