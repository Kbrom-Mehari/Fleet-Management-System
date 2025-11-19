package org.securityapps.vehicleregistry.domain.vehicleowner;

import java.util.Optional;

public interface VehicleOwnerRepository {
    Optional<VehicleOwner> findById(VehicleOwnerId id);
    Optional<VehicleOwner> findVehicleOwnerByPhoneNumber(String phoneNumber);
    VehicleOwner save(VehicleOwner vehicleOwner);
}
