package org.securityapps.vehicleregistry.application.usecase.vehicleowner;

import org.securityapps.vehicleregistry.application.dto.VehicleTransferredResponse;
import org.securityapps.vehicleregistry.domain.vehicle.VehicleId;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;

public interface TransferVehicleOwnershipUseCase {
    VehicleTransferredResponse transferVehicleOwnership(VehicleId vehicleId, VehicleOwnerId newOwner);
}
