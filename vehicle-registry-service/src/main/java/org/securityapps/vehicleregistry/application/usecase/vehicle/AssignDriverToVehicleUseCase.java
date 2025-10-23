package org.securityapps.vehicleregistry.application.usecase.vehicle;

import org.securityapps.vehicleregistry.application.dto.DriverAssignedResponse;
import org.securityapps.vehicleregistry.domain.driver.DriverId;
import org.securityapps.vehicleregistry.domain.vehicle.VehicleId;

public interface AssignDriverToVehicleUseCase {
  DriverAssignedResponse assignDriverToVehicle(VehicleId vehicleId, DriverId driverId);
}
