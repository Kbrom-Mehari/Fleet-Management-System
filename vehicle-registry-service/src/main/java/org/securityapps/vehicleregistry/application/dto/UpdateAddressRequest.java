package org.securityapps.vehicleregistry.application.dto;

public record UpdateAddressRequest(String region, String city, String woreda, String kebele,String phoneNumber) {

}
