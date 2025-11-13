package org.securityapps.vehicletracking.infrastructure.timescaledb.query;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicletracking.common.dto.VehicleLocationPoint;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationQueryService {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VehicleLocationPoint getLastLocation(String deviceId) {
        String sql = """
                SELECT ST_Y(location::geometry) AS latitude,
                ST_X(location::geometry) AS longitude,
                speed,
                heading,
                recorded_at,
                FROM tracking_point 
                WHERE device_id=:deviceId
                ORDER BY recorded_at DESC 
                LIMIT 1;
                """;
        jdbcTemplate.queryForObject(sql,)
    }

}
