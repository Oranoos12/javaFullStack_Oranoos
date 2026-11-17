package be.pxl.inventory.exception;

public class StockItemNotFoundException extends RuntimeException {

    public StockItemNotFoundException(Long ingredientId) {
        super("Er is geen voorraad voor ingrediënt " + ingredientId + ".");
    }
}
