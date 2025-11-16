package org.securityapps.vehicletracking.infrastructure.timescaledb.query;

import org.securityapps.vehicletracking.common.dto.VehicleLocationPoint;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationPointRowMapper implements RowMapper<VehicleLocationPoint> {
    @Override
    public VehicleLocationPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new VehicleLocationPoint(
                rs.getString("device_id"),
                rs.getString("session_id"),
                rs.getDouble("latitude"),
                rs.getDouble("longitude"),
                rs.getObject("speed", Double.class),
                rs.getObject("heading", Double.class),
                rs.getTimestamp("recorded_at").toInstant()
        );
    }
}
