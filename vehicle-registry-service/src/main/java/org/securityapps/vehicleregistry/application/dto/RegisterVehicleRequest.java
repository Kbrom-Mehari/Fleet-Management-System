package org.securityapps.vehicleregistry.application.dto;

public record RegisterVehicleRequest(
        String model,
        String plateNumber,
        String vin ) { }
