package be.pxl.order.exception;

public class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException(String serviceName) {
        super(serviceName + " is momenteel niet bereikbaar. Probeer het later opnieuw.");
    }
}
