package org.securityapps.vehicleregistry.application.dto;

import org.securityapps.vehicleregistry.common.valueObject.Address;
import org.securityapps.vehicleregistry.domain.driver.DriverId;

public record RegisterDriverResponse(
        DriverId driverId,
        String firstName,
        String lastName,
        String licenseNumber,
        Address address) { }
