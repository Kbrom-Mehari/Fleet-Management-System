package org.securityapps.vehicletracking.infrastructure.messaging.kafka.model;

public record GpsRecordedEvent(
        String imei,
        double latitude,
        double longitude,
        double altitude,
        int satellites,
        int course )
{ }