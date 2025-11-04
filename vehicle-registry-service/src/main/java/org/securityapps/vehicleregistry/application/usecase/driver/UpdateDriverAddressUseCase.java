package org.securityapps.vehicleregistry.application.usecase.driver;

import org.securityapps.vehicleregistry.application.dto.UpdateAddressRequest;
import org.securityapps.vehicleregistry.domain.driver.DriverId;

public interface UpdateDriverAddressUseCase {
    void updateAddress(DriverId driverId,UpdateAddressRequest request);
}
