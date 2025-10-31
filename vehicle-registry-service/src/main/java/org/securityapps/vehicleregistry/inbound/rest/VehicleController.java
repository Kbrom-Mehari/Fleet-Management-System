package org.securityapps.vehicleregistry.inbound.rest;

import org.securityapps.vehicleregistry.inbound.rest.dto.ApiResponse;
import org.securityapps.vehicleregistry.application.dto.RegisterVehicleRequest;
import org.securityapps.vehicleregistry.application.dto.RegisterVehicleResponse;
import org.securityapps.vehicleregistry.application.service.VehicleApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/vehicle")
public class VehicleController {
    private final VehicleApplicationService vehicleApplicationService;

    public VehicleController(VehicleApplicationService vehicleApplicationService) {
        this.vehicleApplicationService = vehicleApplicationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterVehicleResponse>> registerVehicle(RegisterVehicleRequest registerVehicleRequest) {
        RegisterVehicleResponse response=vehicleApplicationService.registerVehicle(registerVehicleRequest);
        return ResponseEntity.ok(ApiResponse.success("vehicle registered successfully",response));
    }
}
