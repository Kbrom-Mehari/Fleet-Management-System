package org.securityapps.vehicleregistry.domain.address;

import java.util.UUID;

public record AddressId(String id) {

    public AddressId{
        if(id==null||id.isEmpty()) {
            throw new IllegalArgumentException("id cannot be null or blank");
        }
    }

    public static AddressId newId(){
        return new AddressId(UUID.randomUUID().toString());
    }
    public static AddressId of(String id){
        return new AddressId(id);
    }
}
