package org.securityapps.vehicleregistry.application.dto;

import org.securityapps.vehicleregistry.domain.address.AddressId;

public record RegisterDriverRequest(
        String firstName,
        String lastName,
        String licenseNumber ) { }
