package org.securityapps.vehicleregistry.infrastructure.persistence.repository;

import org.securityapps.vehicleregistry.infrastructure.persistence.entity.VehicleOwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaVehicleOwnerRepository extends JpaRepository<VehicleOwnerEntity, String> {
    Optional<VehicleOwnerEntity> findVehicleOwnerByAddressPhoneNumber(String phoneNumber);
}
