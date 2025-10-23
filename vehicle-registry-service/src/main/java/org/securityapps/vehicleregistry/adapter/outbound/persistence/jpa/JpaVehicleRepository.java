package org.securityapps.vehicleregistry.adapter.outbound.persistence.jpa;

import org.securityapps.vehicleregistry.domain.vehicle.Vehicle;
import org.securityapps.vehicleregistry.domain.vehicle.VehicleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaVehicleRepository extends JpaRepository<Vehicle, VehicleId> {
    Optional<Vehicle> findVehicleByPlateNumber(String plateNumber);
    Optional<Vehicle> findVehicleByVin(String vin);
}
