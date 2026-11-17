package be.pxl.recipe.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RecipeIngredientRequest(
        @NotNull(message = "Ingrediënt is verplicht")
        Long ingredientId,
        @Positive(message = "Hoeveelheid moet groter zijn dan 0")
        double quantityPerPerson
) {
}