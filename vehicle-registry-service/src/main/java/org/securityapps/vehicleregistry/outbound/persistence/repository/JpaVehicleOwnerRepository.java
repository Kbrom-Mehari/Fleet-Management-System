package org.securityapps.vehicleregistry.outbound.persistence.repository;

import org.securityapps.vehicleregistry.outbound.persistence.entity.VehicleOwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaVehicleOwnerRepository extends JpaRepository<VehicleOwnerEntity, String> {
}
