package be.pxl.order.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record OrderRequest(
        @Positive(message = "Aantal personen moet groter zijn dan 0")
        int persons,
        @NotNull(message = "Leverslot is verplicht")
        Long slotId,
        @NotNull(message = "Adres is verplicht")
        @Valid
        AddressDto address,
        @NotEmpty(message = "Kies minstens één recept")
        List<Long> recipeIds

) {
}
