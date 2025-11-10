package org.securityapps.vehicletracking.application.dto;

import org.securityapps.vehicletracking.domain.trackerDevice.TrackerDeviceId;

public record RecordVehicleMovementRequest(TrackerDeviceId trackerDeviceId,
                                           String vehicleId,
                                           TrackingPoint trackingPoint) {
}
