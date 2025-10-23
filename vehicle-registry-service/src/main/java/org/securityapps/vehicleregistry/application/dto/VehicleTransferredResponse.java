package org.securityapps.vehicleregistry.application.dto;

import org.securityapps.vehicleregistry.domain.vehicle.VehicleId;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;

import java.time.Instant;

public record VehicleTransferredResponse(
        VehicleId vehicleId,
        VehicleOwnerId oldOwner,
        VehicleOwnerId newOwner,
        Instant timestamp ) { }
