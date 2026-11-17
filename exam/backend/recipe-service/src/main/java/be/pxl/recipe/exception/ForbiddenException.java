package be.pxl.recipe.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String requiredRole) {

        super("Enkel de rol " + requiredRole + " mag dit doen.");
    }

}