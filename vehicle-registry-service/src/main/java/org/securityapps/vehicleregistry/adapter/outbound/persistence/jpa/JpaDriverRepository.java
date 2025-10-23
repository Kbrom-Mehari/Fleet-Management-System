package org.securityapps.vehicleregistry.adapter.outbound.persistence.jpa;

import org.securityapps.vehicleregistry.domain.driver.Driver;
import org.securityapps.vehicleregistry.domain.driver.DriverId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaDriverRepository extends JpaRepository<Driver, DriverId> {
  fin
}
