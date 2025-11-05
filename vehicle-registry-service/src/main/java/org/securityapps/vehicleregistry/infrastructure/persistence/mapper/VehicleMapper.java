package org.securityapps.vehicleregistry.infrastructure.persistence.mapper;

import org.securityapps.vehicleregistry.domain.driver.DriverId;
import org.securityapps.vehicleregistry.domain.vehicle.Vehicle;
import org.securityapps.vehicleregistry.domain.vehicle.VehicleId;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;
import org.securityapps.vehicleregistry.infrastructure.persistence.entity.VehicleEntity;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {
    public Vehicle toDomain(VehicleEntity vehicleEntity) {
        return Vehicle.create(VehicleId.from(vehicleEntity.getVehicleId()), DriverId.of(vehicleEntity.getAssignedDriverId()), VehicleOwnerId.of(vehicleEntity.getVehicleOwnerId()),vehicleEntity.getModelName(),vehicleEntity.getPlateNumber(),vehicleEntity.getVin());
    }
    public VehicleEntity toEntity(Vehicle vehicle) {
        return new VehicleEntity(vehicle.getVehicleId().toString(),vehicle.getVehicleOwnerId().toString(),vehicle.getAssignedDriverId().toString(),vehicle.getModelName(),vehicle.getPlateNumber(), vehicle.getVin());
    }
}
