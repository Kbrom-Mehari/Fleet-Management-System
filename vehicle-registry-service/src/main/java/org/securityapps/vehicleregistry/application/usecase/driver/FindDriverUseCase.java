package org.securityapps.vehicleregistry.application.usecase.driver;

import org.securityapps.vehicleregistry.domain.driver.Driver;
import org.securityapps.vehicleregistry.domain.driver.DriverId;

public interface FindDriverUseCase {
    Driver findDriverById(DriverId driverId);
    Driver findDriverByPhoneNumber(String phoneNumber);
}
