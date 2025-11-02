package org.securityapps.vehicleregistry.outbound.persistence.repository;

import org.securityapps.vehicleregistry.outbound.persistence.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDriverRepository extends JpaRepository<DriverEntity, String> {

}
