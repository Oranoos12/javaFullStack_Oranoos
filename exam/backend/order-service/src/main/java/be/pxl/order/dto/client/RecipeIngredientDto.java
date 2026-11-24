package be.pxl.order.dto.client;

public record RecipeIngredientDto(Long ingredientId, String name, String unit, double quantityPerPerson) {
}
