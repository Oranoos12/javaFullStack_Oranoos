package be.pxl.order.client;

import be.pxl.order.dto.client.AvailabilityRequest;
import be.pxl.order.dto.client.AvailabilityResponse;
import be.pxl.order.exception.ServiceUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InventoryClientFallback implements InventoryClient {

    private static final Logger log = LoggerFactory.getLogger(InventoryClientFallback.class);

    @Override
    public AvailabilityResponse checkAvailability(AvailabilityRequest request) {
        log.warn("inventory-service onbereikbaar, bestelling wordt geweigerd");
        throw new ServiceUnavailableException("inventory-service");
    }
}
