package org.securityapps.vehicleregistry.application.dto;

import org.securityapps.vehicleregistry.domain.driver.DriverId;
import org.securityapps.vehicleregistry.domain.vehicle.VehicleId;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;

public record RegisterVehicleResponse(
        VehicleId id,
        DriverId assignedDriverId,
        VehicleOwnerId vehicleOwnerId,
        String model,
        String plateNumber,
        String vin) { }
