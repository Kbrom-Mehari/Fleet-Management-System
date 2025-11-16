package org.securityapps.vehicletracking.application.usecase;

import org.securityapps.vehicletracking.domain.trackerDevice.TrackerDeviceId;
import org.securityapps.vehicletracking.domain.vehicleTrackingSession.VehicleTrackingSession;

public interface ManageVehicleTrackingSessionUseCase {
    void endSession(TrackerDeviceId deviceId);
    VehicleTrackingSession getOrCreateSession(TrackerDeviceId deviceId);
}
