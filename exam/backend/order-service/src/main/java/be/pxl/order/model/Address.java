package be.pxl.order.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Address {
    private String street;
    private String number;
    private String postalCode;
    private String city;

    public Address(String street, String number, String postalCode, String city) {
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
        this.city = city;
    }



    public String toText() {
        return street + " " + number + ", " + postalCode + " " + city;
    }
}
