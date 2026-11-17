package be.pxl.recipe.dto.request;

import jakarta.validation.constraints.NotBlank;

public record IngredientRequest(
        @NotBlank(message = "Naam is verplicht")
        String name,
        @NotBlank(message = "Eenheid is verplicht")
        String unit
) {
}