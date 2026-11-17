package be.pxl.inventory.dto.response;

public record StockItemResponse(
        Long id,
        Long ingredientId,
        String ingredientName,
        String unit,
        double quantityAvailable,
        double minimumLevel,
        boolean low
) {
}
