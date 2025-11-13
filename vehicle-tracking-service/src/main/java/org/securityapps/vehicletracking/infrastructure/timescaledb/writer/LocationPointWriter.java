package org.securityapps.vehicletracking.infrastructure.timescaledb.writer;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicletracking.common.dto.VehicleLocationPoint;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LocationPointWriter {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public void save(VehicleLocationPoint locationPoint){
        String sql = """
                INSERT INTO tracking_point(
                device_id,
                session_id,
                location,
                speed,
                heading,
                recorded_at
                )
                VALUES (
                :deviceId,
                :sessionId,
                ST_SetSRID(ST_MakePoint(:longitude,:latitude),4326),
                :speed,
                :heading,
                NOW());
                """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("deviceId",locationPoint.trackerDeviceId())
                .addValue("sessionId",locationPoint.sessionId())
                .addValue("longitude",locationPoint.longitude())
                .addValue("latitude",locationPoint.latitude())
                .addValue("speed",locationPoint.speed())
                .addValue("heading",locationPoint.heading());

        jdbcTemplate.update(sql, params);

    }
}
