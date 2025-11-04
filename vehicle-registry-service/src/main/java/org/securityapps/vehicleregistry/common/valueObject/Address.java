package org.securityapps.vehicleregistry.common.valueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor
public class Address {

    private String kebeleIdNumber;
    private String faydaNumber;
    private String region;
    private String city;
    private String woreda;
    private String kebele;
    private String phoneNumber;



    public Address(String kebeleIdNumber, String faydaNumber, String region, String city, String woreda, String kebele, String phoneNumber) {

        this.region = validate(region, "region");
        this.city = validate(city, "city");
        this.woreda = validate(woreda, "woreda");
        this.kebele = validate(kebele, "kebele");
        this.phoneNumber = validate(phoneNumber, "phoneNumber");
        this.faydaNumber = validate(faydaNumber, "faydaNumber");
        this.kebeleIdNumber = validate(kebeleIdNumber, "kebeleIdNumber");
    }

    //Factory method
    public static Address create(String kebeleIdNumber, String faydaNumber, String region, String city, String woreda, String kebele, String phoneNumber) {
        return new Address(kebeleIdNumber,faydaNumber, region, city, woreda, kebele, phoneNumber);
    }

    private String validate(String value,String fieldName){
        if(value==null || value.trim().isEmpty()){
            throw new IllegalArgumentException(fieldName+" cannot be null or empty");
        }
        return value.trim();
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) return true;
        if(!(object instanceof Address address)) return false;
        return Objects.equals(region, address.region)&&
                Objects.equals(city,address.city)&&
                Objects.equals(woreda,address.woreda)&&
                Objects.equals(kebele,address.kebele)&&
                Objects.equals(phoneNumber,address.phoneNumber);
    }
    @Override
    public int hashCode(){
        return Objects.hash(region,city,woreda,kebele,phoneNumber);
    }
    @Override
    public String toString(){
        return "Region: %s, City: %s, Woreda: %s, Kebele: %s, PhoneNumber: %s".formatted(region,city,woreda,kebele,phoneNumber);
    }


}
