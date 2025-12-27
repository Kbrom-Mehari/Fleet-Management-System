package org.securityapps.vehicletracking.infrastructure.messaging.kafka.model;

public record GpsRecordedEvent(
        String imei,
        double latitude,
        double longitude,
        double altitude,
        double speed,
        int satellites,
        int course )
{ }