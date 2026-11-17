package be.pxl.recipe.exception;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(Long id) {

        super("Recept met id " + id + " bestaat niet.");

    }}
