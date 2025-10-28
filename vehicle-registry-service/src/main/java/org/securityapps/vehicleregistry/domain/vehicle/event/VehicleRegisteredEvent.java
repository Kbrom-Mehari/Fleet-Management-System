package org.securityapps.vehicleregistry.domain.vehicle.event;

import org.securityapps.vehicleregistry.domain.vehicle.VehicleId;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;

import java.time.Instant;

public record VehicleRegisteredEvent(VehicleId vehicleId, Instant registeredAt) {

}
