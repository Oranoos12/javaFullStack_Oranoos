package be.pxl.order.exception;

import be.pxl.order.model.OrderStatus;

public class OrderNotCancellableException extends RuntimeException {

    public OrderNotCancellableException(Long id, OrderStatus status) {
        super("Bestelling " + id + " kan niet meer geannuleerd worden, status is " + status + ".");
    }
}
