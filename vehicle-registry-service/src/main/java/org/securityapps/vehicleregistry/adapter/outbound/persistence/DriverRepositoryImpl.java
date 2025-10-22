package org.securityapps.vehicleregistry.adapter.outbound.persistence;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicleregistry.adapter.outbound.persistence.jpa.JpaDriverRepository;
import org.securityapps.vehicleregistry.domain.driver.Driver;
import org.securityapps.vehicleregistry.domain.driver.DriverId;
import org.securityapps.vehicleregistry.domain.driver.DriverRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DriverRepositoryImpl implements DriverRepository {
    private final JpaDriverRepository jpaDriverRepository;
    public Driver save(Driver driver){
        return jpaDriverRepository.save(driver);
    }
    public Optional<Driver> findById(DriverId id) {
        return jpaDriverRepository.findById(id);
    }
}
