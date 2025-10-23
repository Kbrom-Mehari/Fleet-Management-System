package org.securityapps.vehicleregistry.application.dto;

import org.securityapps.vehicleregistry.domain.vehicle.VehicleId;

public record RegisterVehicleResponse(
        VehicleId id,
        String model,
        String plateNumber,
        String vin) { }
