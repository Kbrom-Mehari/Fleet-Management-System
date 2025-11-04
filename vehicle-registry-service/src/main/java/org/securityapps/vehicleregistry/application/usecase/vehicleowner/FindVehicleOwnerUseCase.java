package org.securityapps.vehicleregistry.application.usecase.vehicleowner;

import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwner;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;

public interface FindVehicleOwnerUseCase {
    VehicleOwner findVehicleOwner(VehicleOwnerId vehicleOwnerId);
    VehicleOwner findVehicleOwnerByPhoneNumber(String phoneNumber);
}
