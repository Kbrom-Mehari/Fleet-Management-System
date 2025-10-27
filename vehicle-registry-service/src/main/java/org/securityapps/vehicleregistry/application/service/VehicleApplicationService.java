package org.securityapps.vehicleregistry.application.service;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicleregistry.application.dto.*;
import org.securityapps.vehicleregistry.application.usecase.vehicle.AssignDriverToVehicleUseCase;
import org.securityapps.vehicleregistry.application.usecase.vehicle.RegisterVehicleUseCase;
import org.securityapps.vehicleregistry.application.usecase.vehicleowner.TransferVehicleOwnershipUseCase;
import org.securityapps.vehicleregistry.domain.driver.DriverId;
import org.securityapps.vehicleregistry.domain.vehicle.Vehicle;
import org.securityapps.vehicleregistry.domain.vehicle.VehicleId;
import org.securityapps.vehicleregistry.domain.vehicle.VehicleRepository;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VehicleApplicationService implements
        RegisterVehicleUseCase,
        AssignDriverToVehicleUseCase,
        TransferVehicleOwnershipUseCase {
    private final VehicleRepository vehicleRepository;
    @Override
    public RegisterVehicleResponse registerVehicle(RegisterVehicleRequest request){
        Vehicle vehicle=Vehicle.register(VehicleId.newId(),request.model(),request.plateNumber(),request.vin());
        vehicleRepository.save(vehicle);
        return new RegisterVehicleResponse(vehicle.getId(),vehicle.getModelName(),vehicle.getPlateNumber(),vehicle.getVin());
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
