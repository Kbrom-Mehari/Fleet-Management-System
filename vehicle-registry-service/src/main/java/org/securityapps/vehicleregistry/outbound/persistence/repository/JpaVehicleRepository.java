package org.securityapps.vehicleregistry.outbound.persistence.repository;

import org.securityapps.vehicleregistry.domain.vehicle.Vehicle;
import org.securityapps.vehicleregistry.domain.vehicle.VehicleId;
import org.securityapps.vehicleregistry.outbound.persistence.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaVehicleRepository extends JpaRepository<VehicleEntity, String> {
    Optional<VehicleEntity> findVehicleByPlateNumber(String plateNumber);
    Optional<VehicleEntity> findVehicleByVin(String vin);
}
