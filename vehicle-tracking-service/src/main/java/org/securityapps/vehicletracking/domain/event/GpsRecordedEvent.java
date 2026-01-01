package org.securityapps.vehicletracking.domain.event;

import java.time.Instant;

public record GpsRecordedEvent(
        String imei,
        double latitude,
        double longitude,
        double altitude,
        double speed,
        int satellites,
        int angle,
        Instant deviceTime,
        Instant receivedAt)
{ }