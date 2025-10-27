package org.securityapps.vehicleregistry.domain.vehicle.event;

import org.securityapps.vehicleregistry.domain.driver.DriverId;
import org.securityapps.vehicleregistry.domain.vehicle.VehicleId;

public record DriverReassignedEvent(DriverId oldDriverId, DriverId newDriverId, VehicleId vehicleId) {
}
