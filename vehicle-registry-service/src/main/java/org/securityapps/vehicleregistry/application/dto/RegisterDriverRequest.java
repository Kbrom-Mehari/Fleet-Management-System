package org.securityapps.vehicleregistry.application.dto;

import org.securityapps.vehicleregistry.common.valueObject.Address;

public record RegisterDriverRequest(
        String firstName,
        String lastName,
        String licenseNumber,
        Address address) { }
