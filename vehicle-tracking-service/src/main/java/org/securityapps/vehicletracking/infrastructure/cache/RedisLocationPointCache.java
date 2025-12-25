package org.securityapps.vehicletracking.infrastructure.cache;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicletracking.common.dto.VehicleLocationPoint;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RedisLocationPointCache {
    private final RedisTemplate<String, VehicleLocationPoint> redisTemplate;

    private static final String LAST_LOCATION_POINT_KEY_PREFIX="last_location_point_of:";
    private static final Duration TTL = Duration.ofMinutes(30);

    public void save (VehicleLocationPoint locationPoint) {
        String key = LAST_LOCATION_POINT_KEY_PREFIX + locationPoint.trackerDeviceId();
        redisTemplate.opsForValue().set(key,locationPoint,TTL);
    }
    public VehicleLocationPoint getLastLocationPoint(String trackerDeviceId) {
        String key = LAST_LOCATION_POINT_KEY_PREFIX + trackerDeviceId;
        return redisTemplate.opsForValue().get(key);
    }
    public void removeLastLocationPoint(String trackerDeviceId) {
        String key = LAST_LOCATION_POINT_KEY_PREFIX + trackerDeviceId;
        redisTemplate.delete(key);
    }
}
