package org.securityapps.vehicletracking.infrastructure.cache;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicletracking.domain.vehicleTrackingSession.VehicleTrackingSession;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RedisTrackingSessionCache {
    private final RedisTemplate<String, VehicleTrackingSession> redisTemplate;

    private static final String SESSION_KEY_PREFIX="tracking_session:";
    private static final Duration SESSION_TTL= Duration.ofMinutes(30);

    public VehicleTrackingSession getActiveSession(String deviceId) {
        String key=SESSION_KEY_PREFIX + deviceId;
        return redisTemplate.opsForValue().get(key);
    }
    public void cacheActiveSession(VehicleTrackingSession session) {
        String key=SESSION_KEY_PREFIX + session.getTrackerDeviceId().toString();
        redisTemplate.opsForValue().set(key,session,SESSION_TTL);
    }
    public void removeActiveSession(String deviceId) {
        String key=SESSION_KEY_PREFIX + deviceId;
        redisTemplate.delete(key);
    }
}
