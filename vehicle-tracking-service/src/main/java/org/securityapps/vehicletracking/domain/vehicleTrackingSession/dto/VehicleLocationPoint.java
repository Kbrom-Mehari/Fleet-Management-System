package org.securityapps.vehicletracking.domain.vehicleTrackingSession.dto;

import java.time.Instant;

public record VehicleLocationPoint(String trackerDeviceId,
                                   String sessionId,
                                   double latitude,
                                   double longitude,
                                   Double speed,
                                   Double heading,
                                   Instant recordedAt) {
}
