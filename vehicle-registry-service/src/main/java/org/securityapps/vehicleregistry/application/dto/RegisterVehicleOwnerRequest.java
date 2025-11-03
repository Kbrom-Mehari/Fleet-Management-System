package org.securityapps.vehicleregistry.application.dto;

import org.securityapps.vehicleregistry.common.valueObject.Address;

public record RegisterVehicleOwnerRequest(
        String firstName,
        String lastName,
        Address address) { }
