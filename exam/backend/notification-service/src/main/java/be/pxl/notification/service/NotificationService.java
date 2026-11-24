package be.pxl.notification.service;

import be.pxl.notification.dto.event.OrderCancelledEvent;
import be.pxl.notification.dto.event.OrderConfirmedEvent;
import be.pxl.notification.dto.event.OrderDeliveredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    public String orderConfirmed(OrderConfirmedEvent event) {
        String message = "Beste " + event.customerId() + ", je bestelling " + event.orderId()
                + " voor " + event.persons() + " personen is bevestigd. We leveren op " + event.address() + ".";
        return send(message);
    }

    public String orderCancelled(OrderCancelledEvent event) {
        String message = "Beste " + event.customerId() + ", je bestelling " + event.orderId() + " is geannuleerd.";
        return send(message);
    }

    public String orderDelivered(OrderDeliveredEvent event) {
        String message = "Je bestelling " + event.orderId() + " is geleverd. Smakelijk!";
        return send(message);
    }

    private String send(String message) {
        log.info("MELDING: {}", message);
        return message;
    }
}