package org.securityapps.vehicleregistry.domain.driver;

import java.util.Optional;

public interface DriverRepository {
    Optional<Driver> findById(DriverId id);
    Optional<Driver> findByPhoneNumber(String phoneNumber);
    Driver save(Driver driver);
}
