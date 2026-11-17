package be.pxl.recipe.dto.response;

import be.pxl.recipe.model.Allergen;

import java.util.List;

public record RecipeResponse(
        Long id,
        String name,
        String description,
        int prepTimeMinutes,
        int persons,
        String week,
        List<Allergen> allergens,
        List<RecipeIngredientResponse> ingredients
) {
}