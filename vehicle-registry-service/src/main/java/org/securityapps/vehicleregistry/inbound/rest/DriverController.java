package org.securityapps.vehicleregistry.inbound.rest;

import org.securityapps.vehicleregistry.inbound.rest.dto.ApiResponse;
import org.securityapps.vehicleregistry.application.dto.AddressDTO;
import org.securityapps.vehicleregistry.application.dto.RegisterDriverRequest;
import org.securityapps.vehicleregistry.application.dto.RegisterDriverResponse;
import org.securityapps.vehicleregistry.application.service.DriverApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/driver")
public class DriverController {
    private final DriverApplicationService driverApplicationService;

    public  DriverController(DriverApplicationService driverApplicationService) {
        this.driverApplicationService = driverApplicationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterDriverResponse>>  registerDriver(RegisterDriverRequest request, AddressDTO addressDTO){
        RegisterDriverResponse response=driverApplicationService.registerDriver(request,addressDTO);
        return ResponseEntity.ok(ApiResponse.success("Driver registered successfully",response));
    }
}
