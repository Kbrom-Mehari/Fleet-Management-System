package org.securityapps.vehicleregistry.domain.vehicle.event;

import org.securityapps.vehicleregistry.domain.driver.DriverId;
import org.securityapps.vehicleregistry.domain.vehicle.VehicleId;

import java.time.Instant;

public record DriverAssignedEvent(DriverId driverId,VehicleId vehicleId,Instant assignedAt) {

}

// cleaner than using a class with getters, constructors, toString(),equals() etc
// in records, all are built-in
