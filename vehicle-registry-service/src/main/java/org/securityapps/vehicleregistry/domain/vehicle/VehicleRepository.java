package org.securityapps.vehicleregistry.domain.vehicle;

import java.util.Optional;

public interface VehicleRepository {
    Optional<Vehicle> findByVin(String vin);
    Optional<Vehicle> findById(VehicleId id);
    Optional<Vehicle> findByPlateNumber(String plateNumber);
    Vehicle save(Vehicle vehicle);
}
