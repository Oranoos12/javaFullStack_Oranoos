package be.pxl.order.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long id) {
        super("Bestelling met id " + id + " bestaat niet.");
    }
}
