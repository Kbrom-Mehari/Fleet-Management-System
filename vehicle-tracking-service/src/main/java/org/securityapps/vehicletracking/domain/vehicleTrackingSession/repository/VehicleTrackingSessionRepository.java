package org.securityapps.vehicletracking.domain.vehicleTrackingSession.repository;

import org.securityapps.vehicletracking.domain.trackerDevice.TrackerDeviceId;
import org.securityapps.vehicletracking.domain.vehicleTrackingSession.VehicleTrackingSession;

import java.util.Optional;

public interface VehicleTrackingSessionRepository {
    void save (VehicleTrackingSession vehicleTracking);
    Optional<VehicleTrackingSession> findActiveSessionByTrackerDeviceId(TrackerDeviceId trackerDeviceId);




}
