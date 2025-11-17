package org.securityapps.vehicletracking.infrastructure.persistence.mapper;

import org.securityapps.vehicletracking.domain.trackerDevice.TrackerDevice;
import org.securityapps.vehicletracking.infrastructure.persistence.entity.TrackerDeviceEntity;
import org.springframework.stereotype.Component;

@Component
public class TrackerDeviceMapper {
    public TrackerDevice toDomain (TrackerDeviceEntity entity) {
        return TrackerDevice.rehydrate(entity.getTrackerDeviceId(), entity.getSerialNumber(), entity.getImei(),
                entity.getSimNumber(), entity.isActive(), entity.isDamaged());
    }
    public TrackerDeviceEntity toEntity (TrackerDevice domain) {
        return new TrackerDeviceEntity(domain.getTrackerDeviceId().toString(), domain.getSerialNumber(), domain.getImei(), domain.getSimNumber(), domain.isActive(), domain.isDamaged());
    }
}
