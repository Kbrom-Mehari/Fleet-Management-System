package org.securityapps.vehicleregistry.domain.driver;

import org.securityapps.vehicleregistry.common.valueObject.Address;

import java.util.Objects;

public class Driver {
    private final DriverId driverId;
    private String firstName;
    private String lastName;
    private final String licenseNumber;
    private Address address;

    private Driver(DriverId driverId,String firstName,String lastName,String licenseNumber,Address address) {
        this.driverId = driverId;
        this.firstName = validate(firstName, "firstName");
        this.lastName = validate(lastName, "lastName");
        this.licenseNumber = validate(licenseNumber, "licenseNumber");
        this.address = address;
    }
    public DriverId getDriverId() {return driverId;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getLicenseNumber() {return licenseNumber;}
    public Address getAddress() {return address;}


    //factory method
    public static Driver create(DriverId driverId,String firstName,String lastName,String licenseNumber,Address address) {
        return new Driver(driverId,firstName,lastName,licenseNumber,address);
    }
    void changeFirstName(String firstName) {
        this.firstName = firstName;
    }
    void changeLastName(String lastName) {
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

    private String validate(String value, String fieldName) {
        if(value==null||value.trim().isEmpty()){
            throw new IllegalArgumentException(fieldName+" cannot be null or empty");
        }
        return value.trim();
    }

    @Override
    public boolean equals(Object object){
        if(this==object) return true;
        if(!(object instanceof Driver driver)) return false;
        return Objects.equals(driverId, driver.driverId);
    }
    @Override
    public int hashCode(){
        return Objects.hash(driverId);
    }

    @Override
    public String toString(){
        return "DriverId: %s, FirstName: %s, LastName: %s, LicenseNumber: %s, Address: (%s)".formatted(driverId,firstName,lastName,licenseNumber,address.toString());
    }

}
