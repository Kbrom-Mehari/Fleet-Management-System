package org.securityapps.vehicleregistry.application.service;

import org.securityapps.vehicleregistry.application.dto.*;
import org.securityapps.vehicleregistry.application.usecase.vehicle.AssignDriverToVehicleUseCase;
import org.securityapps.vehicleregistry.application.usecase.vehicle.RegisterVehicleUseCase;
import org.securityapps.vehicleregistry.application.usecase.vehicle.TransferVehicleOwnershipUseCase;
import org.securityapps.vehicleregistry.domain.driver.DriverId;
import org.securityapps.vehicleregistry.domain.vehicle.VehicleId;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VehicleApplicationService implements
        RegisterVehicleUseCase,
        AssignDriverToVehicleUseCase,
        TransferVehicleOwnershipUseCase {
    @Override
    public RegisterVehicleResponse registerVehicle(RegisterVehicleRequest request){

    }
    @Override
    public DriverAssignedResponse assignDriverToVehicle(VehicleId vehicleId, DriverId driverId) {
        return null;
    }
    @Override
    public VehicleTransferredResponse transferVehicleOwnership(VehicleId vehicleId, VehicleOwnerId oldOwner, VehicleOwnerId newOwner) {
        return null;
    }
}
