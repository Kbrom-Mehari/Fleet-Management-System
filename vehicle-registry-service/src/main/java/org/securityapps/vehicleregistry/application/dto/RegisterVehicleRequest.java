package org.securityapps.vehicleregistry.application.dto;

import org.securityapps.vehicleregistry.common.valueObject.Address;
import org.securityapps.vehicleregistry.domain.driver.DriverId;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;

public record RegisterVehicleRequest(
        VehicleOwnerId vehicleOwnerId,
        DriverId assignedDriverId,
        String model,
        String plateNumber,
        String vin,
        Address address) { }
