package org.securityapps.vehicleregistry.adapter.outbound.persistence.jpa;

import org.securityapps.vehicleregistry.domain.address.Address;
import org.securityapps.vehicleregistry.domain.address.AddressId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAddressRepository extends JpaRepository<Address, AddressId> {
}
