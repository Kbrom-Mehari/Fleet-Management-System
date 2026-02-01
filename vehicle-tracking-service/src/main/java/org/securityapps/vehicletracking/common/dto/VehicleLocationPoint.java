package org.securityapps.vehicletracking.common.dto;

import java.time.Instant;

public record VehicleLocationPoint(String trackerDeviceId,
        String sessionId,
        double latitude,
        double longitude,
        Double speed,
        Double angle,
        Instant deviceTime,
        Instant recordedAt)
{
    public VehicleLocationPoint withSessionId(String sessionId) {
        return new VehicleLocationPoint(trackerDeviceId,
                sessionId,
                latitude,
                longitude,
                speed,
                angle,
                deviceTime,
                recordedAt);
    }
}
