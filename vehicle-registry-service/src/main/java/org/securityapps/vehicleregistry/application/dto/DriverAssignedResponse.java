package org.securityapps.vehicleregistry.application.dto;

import org.securityapps.vehicleregistry.domain.driver.DriverId;
import org.securityapps.vehicleregistry.domain.vehicle.VehicleId;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;

import java.time.Instant;

public record DriverAssignedResponse(
        Instant timestamp,
        VehicleId vehicleId,
        DriverId driverId,
        VehicleOwnerId ownerId ) { }
