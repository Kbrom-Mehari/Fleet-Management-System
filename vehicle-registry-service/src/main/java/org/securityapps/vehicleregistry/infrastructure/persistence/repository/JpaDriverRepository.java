package org.securityapps.vehicleregistry.infrastructure.persistence.repository;

import org.securityapps.vehicleregistry.infrastructure.persistence.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaDriverRepository extends JpaRepository<DriverEntity, String> {
    Optional<DriverEntity> findDriverEntityByAddressPhoneNumber(String phoneNumber);
}