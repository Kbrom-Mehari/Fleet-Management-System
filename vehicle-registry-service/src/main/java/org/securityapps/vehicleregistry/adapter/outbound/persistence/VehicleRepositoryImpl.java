package org.securityapps.vehicleregistry.adapter.outbound.persistence;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicleregistry.adapter.outbound.persistence.jpa.JpaVehicleRepository;
import org.securityapps.vehicleregistry.domain.vehicle.Vehicle;
import org.securityapps.vehicleregistry.domain.vehicle.VehicleId;
import org.securityapps.vehicleregistry.domain.vehicle.VehicleRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VehicleRepositoryImpl implements VehicleRepository {
    private final JpaVehicleRepository jpaVehicleRepository;
    public Vehicle save(Vehicle vehicle) {
        return jpaVehicleRepository.save(vehicle);
    }

    @Override
    public Optional<Vehicle> findById(VehicleId id) {
        return  jpaVehicleRepository.findById(id);
    }
    @Override
    public Optional<Vehicle> findByPlateNumber(String plateNumber) {
        return jpaVehicleRepository.findVehicleByPlateNumber(plateNumber);
    }
    @Override
    public Optional<Vehicle> findByVin(String vin) {
        return jpaVehicleRepository.findVehicleByVin(vin);
    }
}
