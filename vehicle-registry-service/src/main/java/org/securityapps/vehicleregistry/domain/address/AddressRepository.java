package org.securityapps.vehicleregistry.domain.address;

import java.util.Optional;

public interface AddressRepository {
    Address save(Address address);
    Optional<Address> findById(AddressId id);
}
