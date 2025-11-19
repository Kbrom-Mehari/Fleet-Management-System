package org.securityapps.vehicleregistry.domain.vehicleowner;

import lombok.Getter;
import org.securityapps.vehicleregistry.common.valueObject.Address;

import java.util.Objects;

@Getter
public class VehicleOwner {
    private final VehicleOwnerId vehicleOwnerId;
    private String firstName;
    private String lastName;
    private Address address;

    private VehicleOwner(VehicleOwnerId vehicleOwnerId, String firstName,String lastName,Address address) {
        this.vehicleOwnerId = vehicleOwnerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public static VehicleOwner create(VehicleOwnerId vehicleOwnerId, String firstName, String lastName, Address address) {
        return new VehicleOwner(vehicleOwnerId, firstName, lastName, address);
    }
    public void changeFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void changeLastName(String lastName) {
        this.lastName = lastName;
    }

    public void updateAddress(String region, String city, String woreda,String kebele,String phoneNumber) {
        this.address=new Address(this.address.getKebeleIdNumber(),
                this.address.getFaydaNumber(),
                region!=null?region:this.address.getRegion(),
                city!=null?city:this.address.getCity(),
                woreda!=null?woreda:this.address.getWoreda(),
                kebele!=null?kebele:this.address.getKebele(),
                phoneNumber!=null?phoneNumber:this.address.getPhoneNumber());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if(!(object instanceof VehicleOwner owner)) return false;
        return Objects.equals(this.vehicleOwnerId, owner.vehicleOwnerId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(vehicleOwnerId);
    }

    @Override
    public String toString() {
        return "VehicleOwnerId: %s, FirstName: %s, LastName: %s, Address: %s".formatted(vehicleOwnerId, firstName, lastName, address.toString());
    }
}
