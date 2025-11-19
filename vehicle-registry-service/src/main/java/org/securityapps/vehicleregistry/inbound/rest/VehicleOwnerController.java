package org.securityapps.vehicleregistry.inbound.rest;

import org.securityapps.vehicleregistry.application.dto.UpdateAddressRequest;
import org.securityapps.vehicleregistry.application.usecase.vehicleowner.FindVehicleOwnerUseCase;
import org.securityapps.vehicleregistry.application.usecase.vehicleowner.RegisterVehicleOwnerUseCase;
import org.securityapps.vehicleregistry.application.usecase.vehicleowner.UpdateVehicleOwnerAddressUseCase;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwner;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;
import org.securityapps.vehicleregistry.inbound.rest.dto.ApiResponse;
import org.securityapps.vehicleregistry.application.dto.RegisterVehicleOwnerRequest;
import org.securityapps.vehicleregistry.application.dto.RegisterVehicleOwnerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicle-owner")
public class VehicleOwnerController {
    private final FindVehicleOwnerUseCase findVehicleOwnerUseCase;
    private final RegisterVehicleOwnerUseCase registerVehicleOwnerUseCase;
    private final UpdateVehicleOwnerAddressUseCase updateVehicleOwnerAddressUseCase;

    public VehicleOwnerController(FindVehicleOwnerUseCase findVehicleOwnerUseCase,
                                  RegisterVehicleOwnerUseCase registerVehicleOwnerUseCase,
                                  UpdateVehicleOwnerAddressUseCase updateVehicleOwnerAddressUseCase) {

        this.findVehicleOwnerUseCase = findVehicleOwnerUseCase;
        this.registerVehicleOwnerUseCase = registerVehicleOwnerUseCase;
        this.updateVehicleOwnerAddressUseCase = updateVehicleOwnerAddressUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterVehicleOwnerResponse>> registerVehicleOwner(@RequestBody RegisterVehicleOwnerRequest request){
        RegisterVehicleOwnerResponse response=registerVehicleOwnerUseCase.registerVehicleOwner(request);
        return ResponseEntity.ok(ApiResponse.success("Vehicle Owner registered successfully",response));
    }
    @PatchMapping("/{id}/update-address")
    public ResponseEntity<ApiResponse<Object>> updateAddress(@PathVariable VehicleOwnerId id, @RequestBody UpdateAddressRequest request){
        updateVehicleOwnerAddressUseCase.updateAddress(id,request);
        return ResponseEntity.ok(ApiResponse.success("Vehicle Owner updated successfully",null));
    }
    @GetMapping("/{id}/find-by-id")
    public ResponseEntity<VehicleOwner> findVehicleOwnerById(@PathVariable VehicleOwnerId id){
        var vehicleOwner=findVehicleOwnerUseCase.findVehicleOwner(id);
        return ResponseEntity.ok(vehicleOwner);
    }
    @GetMapping("/find-by-phone")
    public  ResponseEntity<VehicleOwner> findVehicleOwnerByPhoneNumber(@RequestParam("phone") String phoneNumber){
        var vehicleOwner=findVehicleOwnerUseCase.findVehicleOwnerByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(vehicleOwner);
    }
}
