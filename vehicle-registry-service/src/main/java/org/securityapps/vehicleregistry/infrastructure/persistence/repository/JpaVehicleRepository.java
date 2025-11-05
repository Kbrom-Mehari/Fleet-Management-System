package org.securityapps.vehicleregistry.infrastructure.persistence.repository;

import org.securityapps.vehicleregistry.infrastructure.persistence.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaVehicleRepository extends JpaRepository<VehicleEntity, String> {
    Optional<VehicleEntity> findVehicleByPlateNumber(String plateNumber);
    Optional<VehicleEntity> findVehicleByVin(String vin);
}
