package org.securityapps.vehicletracking.domain.vehicleTrackingSession.service;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicletracking.domain.trackerDevice.TrackerDeviceId;
import org.securityapps.vehicletracking.domain.vehicleTrackingSession.VehicleTrackingSession;
import org.securityapps.vehicletracking.domain.vehicleTrackingSession.repository.VehicleTrackingSessionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionAnalyticsCalculator {
    private final VehicleTrackingSessionRepository vehicleTrackingSessionRepository;

    public VehicleTrackingSession getOrStartSession(TrackerDeviceId trackerDeviceId) {
        var session=vehicleTrackingSessionRepository.findBySessionByTrackerId(trackerDeviceId);
    }
}
