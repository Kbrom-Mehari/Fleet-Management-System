package org.securityapps.vehicleregistry.inbound.rest;

import org.securityapps.vehicleregistry.application.dto.UpdateAddressRequest;
import org.securityapps.vehicleregistry.application.usecase.driver.FindDriverUseCase;
import org.securityapps.vehicleregistry.application.usecase.driver.RegisterDriverUseCase;
import org.securityapps.vehicleregistry.application.usecase.driver.UpdateDriverAddressUseCase;
import org.securityapps.vehicleregistry.domain.driver.Driver;
import org.securityapps.vehicleregistry.domain.driver.DriverId;
import org.securityapps.vehicleregistry.inbound.rest.dto.ApiResponse;
import org.securityapps.vehicleregistry.application.dto.RegisterDriverRequest;
import org.securityapps.vehicleregistry.application.dto.RegisterDriverResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")
public class DriverController {
    private final RegisterDriverUseCase registerDriverUseCase;
    private final FindDriverUseCase findDriverUseCase;
    private final UpdateDriverAddressUseCase updateDriverAddressUseCase;

    public  DriverController(RegisterDriverUseCase registerDriverUseCase,
                             FindDriverUseCase findDriverUseCase,
                             UpdateDriverAddressUseCase updateDriverAddressUseCase) {

        this.registerDriverUseCase = registerDriverUseCase;
        this.findDriverUseCase = findDriverUseCase;
        this.updateDriverAddressUseCase = updateDriverAddressUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterDriverResponse>>  registerDriver(@RequestBody RegisterDriverRequest request){
        RegisterDriverResponse response=registerDriverUseCase.registerDriver(request);
        return ResponseEntity.ok(ApiResponse.success("Driver registered successfully",response));
    }
    @PatchMapping("/{driverId}/update-address")
    public ResponseEntity<ApiResponse<Object>> updateAddress(@PathVariable DriverId driverId, @RequestBody UpdateAddressRequest request){
        updateDriverAddressUseCase.updateAddress(driverId,request);
        return ResponseEntity.ok(ApiResponse.success("Driver Address updated successfully",null));
    }
    @GetMapping("/{id}/find-by-id")
    public ResponseEntity<Driver> findDriverById(@PathVariable DriverId id){
        var driver= findDriverUseCase.findDriverById(id);
        return ResponseEntity.ok(driver);
    }
    @GetMapping("/find-by-phone")
    public ResponseEntity<Driver> findDriverByPhoneNumber(@RequestParam("phone") String phoneNumber){
        var driver=findDriverUseCase.findDriverByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(driver);
    }

}
