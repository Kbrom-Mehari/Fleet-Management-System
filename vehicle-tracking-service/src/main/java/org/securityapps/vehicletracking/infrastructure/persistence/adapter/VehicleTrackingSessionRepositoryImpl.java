package org.securityapps.vehicletracking.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicletracking.domain.vehicleTrackingSession.VehicleTrackingSession;
import org.securityapps.vehicletracking.domain.vehicleTrackingSession.repository.VehicleTrackingSessionRepository;
import org.securityapps.vehicletracking.infrastructure.persistence.mapper.VehicleTrackingSessionMapper;
import org.securityapps.vehicletracking.infrastructure.persistence.repository.JpaVehicleTrackingSessionRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VehicleTrackingSessionRepositoryImpl implements VehicleTrackingSessionRepository {
    private final JpaVehicleTrackingSessionRepository sessionRepository;
    private final VehicleTrackingSessionMapper mapper;

    @Override
    public void save(VehicleTrackingSession session){
        sessionRepository.save(mapper.toEntity(session));
    }
}