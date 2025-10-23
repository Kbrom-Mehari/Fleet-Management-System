package org.securityapps.vehicleregistry.domain.address;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Address {
    private final AddressId id;
    private final String kebeleIdNumber;
    private final String faydaNumber;
    private String region;
    private String city;
    private String woreda;
    private String kebele;
    private String phoneNumber;

    public Address(AddressId id, String kebeleIdNumber, String faydaNumber, String region, String city, String woreda, String kebele, String phoneNumber) {
        this.id= Objects.requireNonNull(id, "id cannot be null");
        this.region = Objects.requireNonNull(region, "region cannot be null");
        this.city = Objects.requireNonNull(city, "city cannot be null");
        this.woreda = Objects.requireNonNull(woreda, "woreda cannot be null");
        this.kebele = Objects.requireNonNull(kebele, "kebele cannot be null");
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phoneNumber cannot be null");
        this.faydaNumber = Objects.requireNonNull(faydaNumber, "faydaNumber cannot be null");
        this.kebeleIdNumber = Objects.requireNonNull(kebeleIdNumber, "kebeleIdNumber cannot be null");
    }
    //Factory method
    public static Address create(AddressId id,String kebeleIdNumber, String faydaNumber, String region, String city, String woreda, String kebele, String phoneNumber) {
        return new Address(id, kebeleIdNumber,faydaNumber, region, city, woreda, kebele, phoneNumber);
    }

    public void changeRegion(String newRegion) {this.region = newRegion;}
    public void changeCity(String newCity) {this.city = newCity;}
    public void changeWoreda(String newWoreda) {this.woreda = newWoreda;}
    public void changePhoneNumber(String newPhoneNumber) {this.phoneNumber = newPhoneNumber;}
    public void changeKebele(String newKebele) {this.kebele = newKebele;}

    @Override
    public boolean equals(Object object) {
        if(this==object) return true;
        if(!(object instanceof Address address)) return false;
        return Objects.equals(id,address.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
