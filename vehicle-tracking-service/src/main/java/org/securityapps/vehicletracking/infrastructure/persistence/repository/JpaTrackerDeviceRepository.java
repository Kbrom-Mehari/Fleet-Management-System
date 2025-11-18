package org.securityapps.vehicletracking.infrastructure.persistence.repository;

import org.securityapps.vehicletracking.infrastructure.persistence.entity.TrackerDeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaTrackerDeviceRepository extends JpaRepository<TrackerDeviceEntity,String> {
    Optional<TrackerDeviceEntity> findByImei(String imei);
}
