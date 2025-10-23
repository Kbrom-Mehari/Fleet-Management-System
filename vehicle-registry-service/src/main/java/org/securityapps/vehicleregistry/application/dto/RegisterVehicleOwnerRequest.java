package org.securityapps.vehicleregistry.application.dto;

public record RegisterVehicleOwnerRequest(
        String firstName,
        String lastName) { }
