package org.securityapps.vehicleregistry.application.service;

import org.securityapps.vehicleregistry.application.dto.AddressDTO;
import org.securityapps.vehicleregistry.application.dto.RegisterVehicleOwnerRequest;
import org.securityapps.vehicleregistry.application.dto.RegisterVehicleOwnerResponse;
import org.securityapps.vehicleregistry.application.dto.UpdateAddressRequest;
import org.securityapps.vehicleregistry.application.usecase.address.UpdateAddressUseCase;
import org.securityapps.vehicleregistry.application.usecase.vehicleowner.RegisterVehicleOwnerUseCase;
import org.securityapps.vehicleregistry.domain.address.Address;
import org.securityapps.vehicleregistry.domain.address.AddressId;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwner;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VehicleOwnerApplicationService implements RegisterVehicleOwnerUseCase {
    @Override
    public RegisterVehicleOwnerResponse registerVehicleOwner(RegisterVehicleOwnerRequest request, AddressDTO addressDTO){
        Address address=Address.create(AddressId.newId(), addressDTO.kebeleIdNumber(), addressDTO.faydaNumber(), addressDTO.region(), addressDTO.city(), addressDTO.woreda(), addressDTO.kebele(), addressDTO.phoneNumber());
        VehicleOwner owner=VehicleOwner.create(VehicleOwnerId.newId(), request.firstName(), request.lastName(), address.getId());
        return new RegisterVehicleOwnerResponse(owner.getId(), owner.getFirstName(), owner.getLastName(), owner.getAddressId());
    }
    public void updateAddress(UpdateAddressRequest request){

    }
}
