package org.securityapps.vehicletracking.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.securityapps.vehicletracking.application.usecase.ManageVehicleTrackingSessionUseCase;
import org.securityapps.vehicletracking.domain.trackerDevice.TrackerDeviceId;
import org.securityapps.vehicletracking.domain.vehicleTrackingSession.VehicleTrackingSession;
import org.securityapps.vehicletracking.domain.vehicleTrackingSession.repository.VehicleTrackingSessionRepository;
import org.securityapps.vehicletracking.infrastructure.cache.RedisTrackingSessionCache;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class VehicleTrackingSessionManager implements ManageVehicleTrackingSessionUseCase{
    private final Map<String ,VehicleTrackingSession> activeSessions=new ConcurrentHashMap<>();
    private final RedisTrackingSessionCache trackingSessionCache;
    private final VehicleTrackingSessionRepository repository;

    private static final String SESSION_KEY_PREFIX="tracking_session:";
    
    @Override
    public VehicleTrackingSession getOrCreateSession(TrackerDeviceId deviceId) {
        String key=SESSION_KEY_PREFIX+deviceId.toString();

        // check in-memory
        VehicleTrackingSession session=activeSessions.get(key);
        if(session!=null&&!session.isEnded()){
            return session;
        }

        //check redis
        session= trackingSessionCache.getActiveSession(deviceId.toString());
        if(session!=null&&!session.isEnded()){
            activeSessions.put(key,session);
            return session;
        }

        //start new session if not found any
        session=VehicleTrackingSession.start(deviceId);
        activeSessions.put(key,session);
        trackingSessionCache.cacheActiveSession(session);
        return session;

    }
    public void updateSession(VehicleTrackingSession session){
        String key=SESSION_KEY_PREFIX+session.getTrackerDeviceId().toString();
        activeSessions.put(key,session);
        trackingSessionCache.cacheActiveSession(session);
    }
    @Override
    public void endSession(TrackerDeviceId deviceId){
        String key=SESSION_KEY_PREFIX+deviceId.toString();
        VehicleTrackingSession session=activeSessions.remove(key);

        if(session==null){  // if session is not found in-memory we try in redis, (if jvm restarts, it could be lost)
            session=trackingSessionCache.getActiveSession(deviceId.toString());
        }
        if(session==null){ // if session id not found in redis cache, we log the warning - no active session to end
            log.warn("No active session found for deviceId: {} - nothing to end",deviceId);
        }
        if( session.isEnded()){  // if session is already ended, we just remove it from redis
                                //no NPE because session==null is handled first
            log.info("Session is already ended");
            trackingSessionCache.removeActiveSession(deviceId.toString());
        }
        try {
            session.endSession(); // mark session as ended and do analytics - in domain

            repository.save(session); // persist it after clean up

            trackingSessionCache.removeActiveSession(deviceId.toString()); //remove from cache after it's saved to db
            log.info("Successfully ended and persisted session for deviceId: {}",deviceId);

        }
        catch(Exception e){
            log.error("!! Failed to save ended session for deviceId {}: {}",deviceId,e.getMessage());
            activeSessions.put(key,session); // restore session to memory to prevent data loss
            try{
                trackingSessionCache.cacheActiveSession(session); // restore it to redis for retry
                log.warn("session restored to redis for retry(deviceId: {})",deviceId);
            }
            catch(Exception redisEx){
                log.error("!! Failed to restore session for deviceId {}: {}",deviceId,e.getMessage());
            }
        }

    }
    public boolean isSessionActive(TrackerDeviceId deviceId){
        String key=SESSION_KEY_PREFIX+deviceId.toString();
        VehicleTrackingSession session=activeSessions.get(key);
        if(session==null){
            session=trackingSessionCache.getActiveSession(deviceId.toString());
        }
        return session!=null&&!session.isEnded();
    }
}