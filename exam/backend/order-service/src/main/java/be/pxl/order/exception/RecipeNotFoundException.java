package be.pxl.order.exception;

public class RecipeNotFoundException extends RuntimeException {

    public RecipeNotFoundException(Long id) {
        super("Recept met id " + id + " bestaat niet.");
    }
}
