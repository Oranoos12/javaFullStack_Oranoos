package be.pxl.order.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AddressDto(
        @NotBlank(message = "Straat is verplicht")
        String street,
        @NotBlank(message = "Huisnummer is verplicht")
        String number,
        @NotBlank(message = "Postcode is verplicht")
        String postalCode,
        @NotBlank(message = "Gemeente is verplicht")
        String city
) {
}
