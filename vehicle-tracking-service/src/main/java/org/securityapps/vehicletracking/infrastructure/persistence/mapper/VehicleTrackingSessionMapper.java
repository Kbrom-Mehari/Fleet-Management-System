package org.securityapps.vehicletracking.infrastructure.persistence.mapper;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicletracking.domain.vehicleTrackingSession.VehicleTrackingSession;
import org.securityapps.vehicletracking.infrastructure.persistence.entity.VehicleTrackingSessionEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehicleTrackingSessionMapper {
/*
    public VehicleTrackingSession toDomain(VehicleTrackingSessionEntity entity) {
        return VehicleTrackingSession.start(TrackerDeviceId.from(entity.getTrackerDeviceId()));
    }
*/
    public VehicleTrackingSessionEntity toEntity(VehicleTrackingSession domain) {
        return new VehicleTrackingSessionEntity(domain.getSessionId().toString(),domain.getTrackerDeviceId().toString(), domain.getTotalDistanceCovered(), domain.getLastUpdate(),domain.getStartedAt(),domain.getEndedAt());
    }
}
