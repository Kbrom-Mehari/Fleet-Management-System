package org.securityapps.vehicleregistry.domain.vehicleowner;

import lombok.Getter;
import org.securityapps.vehicleregistry.domain.address.AddressId;

import java.util.Objects;

@Getter
public class VehicleOwner {
    private final VehicleOwnerId id;
    private String firstName;
    private String lastName;
    private final AddressId addressId;

    private VehicleOwner(VehicleOwnerId id, String firstName,String lastName,AddressId addressId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressId = addressId;
    }
    public static VehicleOwner register(VehicleOwnerId id, String firstName, String lastName, AddressId addressId) {
        return new VehicleOwner(id, firstName, lastName, addressId);
    }
    public void changeFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void changeLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if(!(object instanceof VehicleOwner owner)) return false;
        return Objects.equals(this.id, owner.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
