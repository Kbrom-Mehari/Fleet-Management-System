package org.securityapps.vehicletracking.application.dto;

import org.securityapps.vehicletracking.domain.vehicleTrackingSession.VehicleTrackingSessionId;

public record RecordVehicleMovementResponse(VehicleTrackingSessionId sessionId,
                                            boolean sessionStarted,
                                            String message) {
}
