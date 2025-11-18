package org.securityapps.vehicletracking.infrastructure.timescaledb.query;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicletracking.common.dto.VehicleLocationPoint;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationQueryService {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final LocationPointRowMapper mapper=new LocationPointRowMapper();

    public Optional<VehicleLocationPoint> getLastLocation(String deviceId) {
        String sql = """
                SELECT
                device_id,
                session_id,
                ST_Y(location::geometry) AS latitude,
                ST_X(location::geometry) AS longitude,
                speed,
                heading,
                recorded_at
                FROM tracking_point
                WHERE device_id=:deviceId
                ORDER BY recorded_at DESC
                LIMIT 1;
                """;
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("deviceId",deviceId);
        List<VehicleLocationPoint> list =jdbcTemplate.query(sql,param,mapper);
        return list.stream().findFirst();

    }
    public List<VehicleLocationPoint> getLocations(String sessionId) {
        String sql = """
                SELECT
                device_id,
                session_id,
                ST_Y(location::geometry) AS latitude,
                ST_X(location::geometry) AS longitude,
                speed,
                heading,
                recorded_at
                FROM tracking_point
                WHERE session_id=:sessionId
                ORDER BY recorded_at ASC;
                """;
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("sessionId",sessionId);
        return jdbcTemplate.query(sql,param,mapper);
    }
    public List<VehicleLocationPoint> getLocationsForLastXHours(String deviceId, int hours){
        String sql = """
                SELECT
                device_id,
                session_id,
                ST_Y(location::geometry) AS latitude,
                ST_X(location::geometry) AS longitude,
                speed,
                heading,
                recorded_at
                FROM tracking_point
                WHERE device_id=:deviceId
                AND recorded_at BETWEEN (NOW()- make_interval(hours=>:hours)) AND NOW()
                ORDER BY recorded_at DESC;
                """;
        MapSqlParameterSource params=new MapSqlParameterSource()
                .addValue("deviceId",deviceId)
                .addValue("hours",hours);
        return jdbcTemplate.query(sql,params,mapper);
    }
    public List<VehicleLocationPoint> getLocationsForLastXDays(String deviceId, int days){
        String sql = """
                SELECT
                device_id,
                session_id,
                ST_Y(location::geometry) AS latitude,
                ST_X(location::geometry) AS longitude,
                speed,
                heading,
                recorded_at
                FROM tracking_point
                WHERE device_id=:deviceId
                AND recorded_at BETWEEN (NOW()- make_interval(days=>:days)) AND NOW()
                ORDER BY recorded_at DESC;
                """;
        MapSqlParameterSource params=new MapSqlParameterSource().addValue("deviceId",deviceId)
                .addValue("days",days);
        return jdbcTemplate.query(sql,params,mapper);
    }
}
