package org.securityapps.vehicleregistry.application.usecase.vehicleowner;

import org.securityapps.vehicleregistry.application.dto.AddressDTO;
import org.securityapps.vehicleregistry.application.dto.RegisterVehicleOwnerRequest;
import org.securityapps.vehicleregistry.application.dto.RegisterVehicleOwnerResponse;

public interface RegisterVehicleOwnerUseCase {
    RegisterVehicleOwnerResponse registerVehicleOwner(RegisterVehicleOwnerRequest registerVehicleOwnerRequest, AddressDTO  addressDTO);
}
