package org.securityapps.vehicleregistry.application.usecase.vehicle;

import org.securityapps.vehicleregistry.application.dto.DriverAssignedResponse;
import org.securityapps.vehicleregistry.domain.driver.DriverId;
import org.securityapps.vehicleregistry.domain.vehicle.VehicleId;

public interface ReassignDriverToVehicleUseCase {
    DriverAssignedResponse reassignDriver(VehicleId vehicleId, DriverId newDriverId);

}