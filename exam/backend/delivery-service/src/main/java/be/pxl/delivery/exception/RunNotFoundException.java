package be.pxl.delivery.exception;

public class RunNotFoundException extends RuntimeException {

    public RunNotFoundException(Long id) {
        super("Rit met id " + id + " bestaat niet.");
    }
}
