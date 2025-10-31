package org.securityapps.vehicleregistry.inbound.rest;

import org.securityapps.vehicleregistry.inbound.rest.dto.ApiResponse;
import org.securityapps.vehicleregistry.application.dto.AddressDTO;
import org.securityapps.vehicleregistry.application.dto.RegisterVehicleOwnerRequest;
import org.securityapps.vehicleregistry.application.dto.RegisterVehicleOwnerResponse;
import org.securityapps.vehicleregistry.application.service.VehicleOwnerApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/vehicle-owner")

public class VehicleOwnerController {
    private final VehicleOwnerApplicationService vehicleOwnerApplicationService;

    public VehicleOwnerController(VehicleOwnerApplicationService vehicleOwnerApplicationService) {
        this.vehicleOwnerApplicationService = vehicleOwnerApplicationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterVehicleOwnerResponse>> registerVehicleOwner(RegisterVehicleOwnerRequest request, AddressDTO addressDTO){
        RegisterVehicleOwnerResponse response=vehicleOwnerApplicationService.registerVehicleOwner(request,addressDTO);
        return ResponseEntity.ok(ApiResponse.success("Vehicle Owner registered successfully",response));
    }
}
