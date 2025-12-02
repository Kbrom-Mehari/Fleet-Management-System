package org.securityapps.vehicletracking.Netty.inbound.model;

import java.time.Instant;

public record GpsMessage(
    String deviceId,
    double latitude,
    double longitude,
    double speedKph,
    double altitude,
    Instant timestamp
) { }
