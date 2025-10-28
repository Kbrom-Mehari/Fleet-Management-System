package org.securityapps.vehicleregistry.application.service;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicleregistry.application.dto.UpdateAddressRequest;
import org.securityapps.vehicleregistry.application.usecase.address.UpdateAddressUseCase;
import org.securityapps.vehicleregistry.domain.address.Address;
import org.securityapps.vehicleregistry.domain.address.AddressId;
import org.securityapps.vehicleregistry.domain.address.AddressRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateAddressService implements UpdateAddressUseCase {
    private final AddressRepository addressRepository;
    @Override
    public void updateAddress(UpdateAddressRequest request, AddressId addressId){
        Address address =addressRepository.findById(addressId).orElseThrow(()->new RuntimeException("address not found"));

    }
}
