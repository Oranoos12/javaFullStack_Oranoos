package be.pxl.recipe.exception;

public class IngredientNotFoundException extends RuntimeException {

    public IngredientNotFoundException(Long id) {

        super("Ingrediënt met id " + id + " bestaat niet.");

    }

}
