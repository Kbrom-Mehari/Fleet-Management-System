package org.securityapps.vehicleregistry.adapter.outbound.persistence;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicleregistry.adapter.outbound.persistence.jpa.JpaAddressRepository;
import org.securityapps.vehicleregistry.domain.address.Address;
import org.securityapps.vehicleregistry.domain.address.AddressId;
import org.securityapps.vehicleregistry.domain.address.AddressRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepository {
    private final JpaAddressRepository jpaAddressRepository;
    @Override
    public Address save(Address address){
        return jpaAddressRepository.save(address);
    }
    @Override
    public Optional<Address> findById(AddressId addressId){
        return jpaAddressRepository.findById(addressId);
    }
}
