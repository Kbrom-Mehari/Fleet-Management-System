package org.securityapps.vehicletracking.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.securityapps.vehicletracking.application.usecase.RecordVehicleMovementUseCase;
import org.securityapps.vehicletracking.common.dto.VehicleLocationPoint;
import org.securityapps.vehicletracking.domain.trackerDevice.TrackerDeviceId;
import org.securityapps.vehicletracking.domain.vehicleTrackingSession.VehicleTrackingSession;
import org.securityapps.vehicletracking.infrastructure.cache.RedisLocationPointCache;
import org.securityapps.vehicletracking.infrastructure.timescaledb.writer.LocationPointWriter;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleMovementManager implements RecordVehicleMovementUseCase {
    private final RedisLocationPointCache redisCache;
    private final VehicleTrackingSessionManager sessionManager;
    private final LocationPointWriter writer;

    @Override
    public void recordMovement(VehicleLocationPoint locationPoint){
        TrackerDeviceId deviceId = TrackerDeviceId.from(locationPoint.trackerDeviceId());
        VehicleTrackingSession session = sessionManager.getOrCreateSession(deviceId);
        var newLocationPoint=locationPoint.withSessionId(session.getSessionId().toString());
        try{
            writer.save(newLocationPoint);
            session.addLocationPoint(newLocationPoint); //add in-memory
            try{
                redisCache.save(newLocationPoint);
                sessionManager.updateSession(session);

            } catch (Exception cacheEx){
                log.warn("Redis cache update failed for device {}: {}",deviceId,cacheEx.getMessage());
            }

        } catch(Exception dbEx){
            log.error("Failed to record location for device {}: {}",deviceId,dbEx.getMessage());
        }
    }
    public VehicleLocationPoint getLastLocationPoint(String trackerDeviceId){
        return redisCache.getLastLocationPoint(trackerDeviceId);
    }
}
