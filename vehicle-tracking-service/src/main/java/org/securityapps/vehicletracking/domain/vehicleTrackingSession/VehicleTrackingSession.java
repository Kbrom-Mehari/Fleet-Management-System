package org.securityapps.vehicletracking.domain.vehicleTrackingSession;

import lombok.Getter;
import org.securityapps.vehicletracking.domain.vehicleTrackingSession.dto.VehicleLocationPoint;
import org.securityapps.vehicletracking.domain.trackerDevice.TrackerDeviceId;
import org.securityapps.vehicletracking.domain.vehicleTrackingSession.dto.SessionAnalytics;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class VehicleTrackingSession {
    private final VehicleTrackingSessionId sessionId;
    private final TrackerDeviceId trackerDeviceId;
    private final List<VehicleLocationPoint> locationPoints=new ArrayList<>();
    private Instant lastUpdate;
    private final Instant startedAt;
    private Instant endedAt;
    private double totalDistanceCovered=0;

    private VehicleTrackingSession(VehicleTrackingSessionId sessionId, TrackerDeviceId trackerDeviceId) {
        this.sessionId = Objects.requireNonNull(sessionId, "Session ID must not be null");
        this.trackerDeviceId =Objects.requireNonNull(trackerDeviceId,"Tracker Device ID must not be null");
        this.startedAt = Instant.now();
    }
    public static VehicleTrackingSession start(TrackerDeviceId trackerDeviceId) {
        return new VehicleTrackingSession(VehicleTrackingSessionId.newId(),trackerDeviceId);
    }

    public void addLocationPoint(VehicleLocationPoint locationPoint) {
        locationPoints.add(locationPoint);
        totalDistanceCovered=0;
        lastUpdate=Instant.now();
    }

    public SessionAnalytics  endSession() {
        endedAt = Instant.now();
        return new SessionAnalytics(0,0,0);
    }
    public boolean isEnded(){return endedAt!=null;}

}
