package org.securityapps.vehicleregistry.outbound.persistence.adapter;

import org.securityapps.vehicleregistry.outbound.persistence.repository.JpaAddressRepository;
import org.securityapps.vehicleregistry.common.valueObject.Address;
import org.securityapps.vehicleregistry.domain.address.AddressId;
import org.securityapps.vehicleregistry.domain.address.AddressRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public class AddressRepositoryImpl implements AddressRepository {
    private final JpaAddressRepository jpaAddressRepository;

    public AddressRepositoryImpl(JpaAddressRepository jpaAddressRepository) {
        this.jpaAddressRepository = jpaAddressRepository;
    }

    @Override
    public Address save(Address address){
        return jpaAddressRepository.save(address);
    }
    @Override
    public Optional<Address> findById(AddressId addressId){
        return jpaAddressRepository.findById(addressId);
    }
}
