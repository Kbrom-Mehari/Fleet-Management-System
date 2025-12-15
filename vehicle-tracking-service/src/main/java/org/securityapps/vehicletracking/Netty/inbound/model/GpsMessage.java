package org.securityapps.vehicletracking.Netty.inbound.model;

import java.time.Instant;
import java.util.Map;

public record GpsMessage(
    String imei,
    double latitude,
    double longitude,
    int speedKph,
    int altitude,
    int angle,
    int satellites,
    int priority,
    int eventId,
    int totalIO,
    Map<Integer,Object> ioElements,
    Instant timestamp
) { }
