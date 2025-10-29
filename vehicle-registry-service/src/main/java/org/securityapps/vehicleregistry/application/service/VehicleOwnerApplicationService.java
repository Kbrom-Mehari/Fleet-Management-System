package org.securityapps.vehicleregistry.application.service;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicleregistry.application.dto.AddressDTO;
import org.securityapps.vehicleregistry.application.dto.RegisterVehicleOwnerRequest;
import org.securityapps.vehicleregistry.application.dto.RegisterVehicleOwnerResponse;
import org.securityapps.vehicleregistry.application.dto.UpdateAddressRequest;
import org.securityapps.vehicleregistry.application.usecase.address.UpdateAddressUseCase;
import org.securityapps.vehicleregistry.application.usecase.vehicleowner.RegisterVehicleOwnerUseCase;
import org.securityapps.vehicleregistry.domain.address.Address;
import org.securityapps.vehicleregistry.domain.address.AddressId;
import org.securityapps.vehicleregistry.domain.address.AddressRepository;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwner;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerRepository;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VehicleOwnerApplicationService implements RegisterVehicleOwnerUseCase {
    private final AddressRepository addressRepository;
    private final VehicleOwnerRepository vehicleOwnerRepository;
    private final UpdateAddressService updateAddressService;

    @Override
    public RegisterVehicleOwnerResponse registerVehicleOwner(RegisterVehicleOwnerRequest request, AddressDTO addressDTO){
        Address address=Address.create(AddressId.newId(), addressDTO.kebeleIdNumber(), addressDTO.faydaNumber(), addressDTO.region(), addressDTO.city(), addressDTO.woreda(), addressDTO.kebele(), addressDTO.phoneNumber());
        VehicleOwner owner=VehicleOwner.register(VehicleOwnerId.newId(), request.firstName(), request.lastName(), address.getId());
        return new RegisterVehicleOwnerResponse(owner.getId(), owner.getFirstName(), owner.getLastName(), owner.getAddressId());
    }
    public void updateAddress(UpdateAddressRequest request,VehicleOwnerId vehicleOwnerId){
        VehicleOwner vehicleOwner=vehicleOwnerRepository.findById(vehicleOwnerId).orElseThrow(()->new RuntimeException("vehicle owner not found"));
        Address address=addressRepository.findById(vehicleOwner.getAddressId()).orElseThrow(()->new RuntimeException("address not found"));
        updateAddressService.updateAddress(request,address.getId());
        addressRepository.save(address);
    }
}
