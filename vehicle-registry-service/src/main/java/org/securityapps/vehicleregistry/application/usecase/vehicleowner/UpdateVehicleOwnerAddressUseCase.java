package org.securityapps.vehicleregistry.application.usecase.vehicleowner;

import org.securityapps.vehicleregistry.application.dto.UpdateAddressRequest;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;

public interface UpdateVehicleOwnerAddressUseCase {
    void  updateAddress(VehicleOwnerId vehicleOwnerId, UpdateAddressRequest request);
}
