package org.securityapps.vehicleregistry.domain.driver;

import lombok.Getter;
import org.securityapps.vehicleregistry.domain.address.AddressId;

import java.util.Objects;

@Getter
public class Driver {
    private final DriverId id;
    private String firstName;
    private String lastName;
    private final String licenseNumber;
    private String phoneNumber;
    private final AddressId addressId;

    public Driver(DriverId id,String firstName,String lastName,String licenseNumber,String phoneNumber,AddressId addressId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.licenseNumber = licenseNumber;
        this.phoneNumber = phoneNumber;
        this.addressId = addressId;
    }

    //factory method
    public static Driver create(DriverId driverId,String firstName,String lastName,String licenseNumber,String phoneNumber,AddressId addressId) {
        return new Driver(driverId,firstName,lastName,licenseNumber,phoneNumber,addressId);
    }
    void changeFirstName(String firstName) {
        this.firstName = firstName;
    }
    void changeLastName(String lastName) {
        this.lastName = lastName;
    }
    void changePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    @Override
    public boolean equals(Object object){
        if(this==object) return true;
        if(!(object instanceof Driver driver)) return false;
        return Objects.equals(id, driver.id);
    }
    @Override
    public int hashCode(){
        return Objects.hash(id);
    }

}
