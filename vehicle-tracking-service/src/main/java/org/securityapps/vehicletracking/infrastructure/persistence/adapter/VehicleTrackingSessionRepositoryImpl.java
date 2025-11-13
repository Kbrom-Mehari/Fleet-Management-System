package org.securityapps.vehicletracking.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicletracking.domain.vehicleTrackingSession.VehicleTrackingSession;
import org.securityapps.vehicletracking.domain.vehicleTrackingSession.repository.VehicleTrackingSessionRepository;
import org.securityapps.vehicletracking.infrastructure.persistence.mapper.VehicleTrackingSessionMapper;
import org.securityapps.vehicletracking.infrastructure.persistence.repository.JpaVehicleTrackingSessionRepository;
import org.securityapps.vehicletracking.infrastructure.timescaledb.writer.LocationPointWriter;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VehicleTrackingSessionRepositoryImpl implements VehicleTrackingSessionRepository {
    private final JpaVehicleTrackingSessionRepository repository;
    private final VehicleTrackingSessionMapper mapper;
    private final LocationPointWriter writer;

    @Override
    public void save(VehicleTrackingSession session){

    }
}