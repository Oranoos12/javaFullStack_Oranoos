package be.pxl.inventory.dto.event;

public record IngredientCreatedEvent(Long ingredientId, String name, String unit) {
}
