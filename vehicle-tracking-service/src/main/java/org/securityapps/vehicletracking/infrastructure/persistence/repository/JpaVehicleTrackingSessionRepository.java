package org.securityapps.vehicletracking.infrastructure.persistence.repository;

import org.securityapps.vehicletracking.infrastructure.persistence.entity.VehicleTrackingSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaVehicleTrackingSessionRepository extends JpaRepository<VehicleTrackingSessionEntity,String> {
    Optional<VehicleTrackingSessionEntity> findVehicleTrackingSessionEntityByTrackerDeviceId(String trackerDeviceId);
}
