CREATE EXTENSION IF NOT EXISTS timescaledb;
CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE IF NOT EXISTS tracking_point(
    id BIGSERIAL PRIMARY KEY,
    device_id VARCHAR(255) NOT NULL,
    session_id VARCHAR(255) NOT NULL,
    location GEOGRAPHY(point,4326) NOT NULL,
    speed DOUBLE PRECISION,
    heading DOUBLE PRECISION,
    recorded_at TIMESTAMPTZ NOT NULL
);

SELECT create_hypertable('tracking_point','recorded_at',if_not_exists =>TRUE);

CREATE INDEX IF NOT EXISTS idx_tracking_point_session_time
    ON tracking_point(session_id,recorded_at DESC);
CREATE INDEX IF NOT EXISTS idx_tracking_point_location
    ON tracking_point USING GIST(location);