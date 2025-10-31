package org.securityapps.vehicleregistry.common.valueObject;

import java.util.Objects;

public class Address {

    private final String kebeleIdNumber;
    private final String faydaNumber;
    private String region;
    private String city;
    private String woreda;
    private String kebele;
    private String phoneNumber;

    public Address(String kebeleIdNumber, String faydaNumber, String region, String city, String woreda, String kebele, String phoneNumber) {

        this.region = Objects.requireNonNull(region, "region cannot be null");
        this.city = Objects.requireNonNull(city, "city cannot be null");
        this.woreda = Objects.requireNonNull(woreda, "woreda cannot be null");
        this.kebele = Objects.requireNonNull(kebele, "kebele cannot be null");
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phoneNumber cannot be null");
        this.faydaNumber = Objects.requireNonNull(faydaNumber, "faydaNumber cannot be null");
        this.kebeleIdNumber = Objects.requireNonNull(kebeleIdNumber, "kebeleIdNumber cannot be null");
    }

    public String getKebeleIdNumber() {return kebeleIdNumber;}
    public String getFaydaNumber() {return faydaNumber;}
    public String getRegion() {return region;}
    public String getCity() {return city;}
    public String getWoreda() {return woreda;}
    public String getKebele() {return kebele;}
    public String getPhoneNumber() {return phoneNumber;}

    //Factory method
    public static Address create(String kebeleIdNumber, String faydaNumber, String region, String city, String woreda, String kebele, String phoneNumber) {
        return new Address(kebeleIdNumber,faydaNumber, region, city, woreda, kebele, phoneNumber);
    }

    public void changeRegion(String newRegion) {this.region = newRegion;}
    public void changeCity(String newCity) {this.city = newCity;}
    public void changeWoreda(String newWoreda) {this.woreda = newWoreda;}
    public void changePhoneNumber(String newPhoneNumber) {this.phoneNumber = newPhoneNumber;}
    public void changeKebele(String newKebele) {this.kebele = newKebele;}


}
