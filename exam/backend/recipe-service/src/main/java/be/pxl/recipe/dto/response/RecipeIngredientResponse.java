package be.pxl.recipe.dto.response;

public record RecipeIngredientResponse(Long ingredientId, String name, String unit, double quantityPerPerson) {
}