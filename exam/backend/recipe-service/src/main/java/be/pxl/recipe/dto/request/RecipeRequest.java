package be.pxl.recipe.dto;

import be.pxl.recipe.model.Allergen;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record RecipeRequest(
        @NotBlank(message = "Naam is verplicht")
        String name,
        String description,
        @Positive(message = "Bereidingstijd moet groter zijn dan 0")
        int prepTimeMinutes,
        @Positive(message = "Aantal personen moet groter zijn dan 0")
        int persons,
        @NotBlank(message = "Week is verplicht")
        String week,
        List<Allergen> allergens,
        @NotEmpty(message = "Een recept heeft minstens één ingrediënt")
        List<@Valid RecipeIngredientRequest> ingredients
) {
}