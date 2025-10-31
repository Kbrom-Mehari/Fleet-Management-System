package org.securityapps.vehicleregistry.inbound.rest.dto;

import java.time.Instant;

public record ApiResponse<T>(boolean success, String message, T data, Instant timestamp) {
    public static  <T> ApiResponse<T> success(String message,T data) {
        return new ApiResponse<T>(true,message,data,Instant.now());
    }
    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<T>(false,message,null,Instant.now());
    }
}
