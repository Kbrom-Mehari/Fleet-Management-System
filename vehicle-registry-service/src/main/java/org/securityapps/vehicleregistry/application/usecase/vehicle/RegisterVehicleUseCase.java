package org.securityapps.vehicleregistry.application.usecase.vehicle;

import org.securityapps.vehicleregistry.application.dto.RegisterVehicleRequest;
import org.securityapps.vehicleregistry.application.dto.RegisterVehicleResponse;

public interface RegisterVehicleUseCase {
    RegisterVehicleResponse registerVehicle(RegisterVehicleRequest request);
}
