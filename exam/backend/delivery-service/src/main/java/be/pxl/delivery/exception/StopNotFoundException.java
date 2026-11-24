package be.pxl.delivery.exception;

public class StopNotFoundException extends RuntimeException {

    public StopNotFoundException(Long runId, Long orderId) {
        super("Bestelling " + orderId + " zit niet in rit " + runId + ".");
    }
}
