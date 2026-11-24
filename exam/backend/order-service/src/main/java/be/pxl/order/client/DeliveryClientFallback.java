package be.pxl.order.client;

import be.pxl.order.dto.client.SlotAvailabilityResponse;
import be.pxl.order.exception.ServiceUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DeliveryClientFallback implements DeliveryClient {

    private static final Logger log = LoggerFactory.getLogger(DeliveryClientFallback.class);

    @Override
    public SlotAvailabilityResponse checkSlot(Long slotId) {
        log.warn("delivery-service onbereikbaar, bestelling wordt geweigerd");
        throw new ServiceUnavailableException("delivery-service");
    }
}
