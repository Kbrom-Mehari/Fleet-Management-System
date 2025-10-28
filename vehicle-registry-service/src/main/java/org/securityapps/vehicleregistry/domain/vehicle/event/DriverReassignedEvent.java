package org.securityapps.vehicleregistry.domain.vehicle.event;

import org.securityapps.vehicleregistry.domain.driver.DriverId;
import org.securityapps.vehicleregistry.domain.vehicle.VehicleId;

import java.time.Instant;

public record DriverReassignedEvent(VehicleId vehicleId, DriverId oldDriverId, DriverId newDriverId, Instant timestamp) {
}
