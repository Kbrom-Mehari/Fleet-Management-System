package org.securityapps.vehicleregistry.inbound.rest;

import org.securityapps.vehicleregistry.application.usecase.vehicle.AssignDriverToVehicleUseCase;
import org.securityapps.vehicleregistry.application.usecase.vehicle.RegisterVehicleUseCase;
import org.securityapps.vehicleregistry.application.usecase.vehicle.TransferVehicleOwnershipUseCase;
import org.securityapps.vehicleregistry.inbound.rest.dto.ApiResponse;
import org.securityapps.vehicleregistry.application.dto.RegisterVehicleRequest;
import org.securityapps.vehicleregistry.application.dto.RegisterVehicleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/vehicles")
public class VehicleController {
    private final RegisterVehicleUseCase registerVehicleUseCase;
    private final AssignDriverToVehicleUseCase assignDriverToVehicleUseCase;
    private final TransferVehicleOwnershipUseCase transferVehicleOwnershipUseCase;


    public VehicleController(RegisterVehicleUseCase registerVehicleUseCase,
                             AssignDriverToVehicleUseCase assignDriverToVehicleUseCase,
                             TransferVehicleOwnershipUseCase transferVehicleOwnershipUseCase) {

        this.registerVehicleUseCase = registerVehicleUseCase;
        this.assignDriverToVehicleUseCase = assignDriverToVehicleUseCase;
        this.transferVehicleOwnershipUseCase = transferVehicleOwnershipUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterVehicleResponse>> registerVehicle(@RequestBody RegisterVehicleRequest request) {
        RegisterVehicleResponse response=registerVehicleUseCase.registerVehicle(request);
        return ResponseEntity.ok(ApiResponse.success("vehicle registered successfully",response));
    }
}
