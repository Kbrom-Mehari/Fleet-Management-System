package org.securityapps.vehicleregistry.domain.vehicleowner;

import java.util.Optional;

public interface VehicleOwnerRepository {
    Optional<VehicleOwner> findById(VehicleOwnerId id);
    VehicleOwner save(VehicleOwner vehicleOwner);
}
