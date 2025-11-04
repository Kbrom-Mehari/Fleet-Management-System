package org.securityapps.vehicleregistry.application.service;

import org.securityapps.vehicleregistry.application.dto.RegisterVehicleOwnerRequest;
import org.securityapps.vehicleregistry.application.dto.RegisterVehicleOwnerResponse;
import org.securityapps.vehicleregistry.application.dto.UpdateAddressRequest;
import org.securityapps.vehicleregistry.application.usecase.vehicleowner.FindVehicleOwnerUseCase;
import org.securityapps.vehicleregistry.application.usecase.vehicleowner.RegisterVehicleOwnerUseCase;
import org.securityapps.vehicleregistry.application.usecase.vehicleowner.UpdateVehicleOwnerAddressUseCase;
import org.securityapps.vehicleregistry.common.exception.ResourceNotFoundException;
import org.securityapps.vehicleregistry.common.valueObject.Address;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwner;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional

public class VehicleOwnerApplicationService implements RegisterVehicleOwnerUseCase,
        UpdateVehicleOwnerAddressUseCase,
        FindVehicleOwnerUseCase {
    private final VehicleOwnerRepository vehicleOwnerRepository;

    public VehicleOwnerApplicationService(VehicleOwnerRepository vehicleOwnerRepository){
        this.vehicleOwnerRepository = vehicleOwnerRepository;
    }

    @Override
    public RegisterVehicleOwnerResponse registerVehicleOwner(RegisterVehicleOwnerRequest request){
        Address address=new Address(request.address().getKebeleIdNumber(), request.address().getFaydaNumber(),request.address().getRegion(),request.address().getCity(),request.address().getWoreda(),request.address().getKebele(),request.address().getPhoneNumber());
        VehicleOwner owner=VehicleOwner.create(VehicleOwnerId.newId(), request.firstName(), request.lastName(), address);
        return new RegisterVehicleOwnerResponse(owner.getVehicleOwnerId(), owner.getFirstName(), owner.getLastName(), owner.getAddress());
    }
    @Override
    public void updateAddress(VehicleOwnerId vehicleOwnerId,UpdateAddressRequest request){
        VehicleOwner vehicleOwner=vehicleOwnerRepository.findById(vehicleOwnerId).orElseThrow(()->new ResourceNotFoundException("vehicle owner not found"));
        vehicleOwner.updateAddress(request.region(), request.city(), request.woreda(), request.kebele(), request.phoneNumber());
        vehicleOwnerRepository.save(vehicleOwner);
    }
    @Override
    public VehicleOwner findVehicleOwner(VehicleOwnerId id){
        return vehicleOwnerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("vehicle owner not found"));
    }
    @Override
    public VehicleOwner findVehicleOwnerByPhoneNumber(String phoneNumber){
        return vehicleOwnerRepository.findVehicleOwnerByPhoneNumber(phoneNumber).orElseThrow(()->new ResourceNotFoundException("vehicle owner not found"));
    }
}
