package org.securityapps.vehicleregistry.adapter.outbound.persistence.jpa;

import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwner;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaVehicleOwnerRepository extends JpaRepository<VehicleOwner, VehicleOwnerId> {
}
