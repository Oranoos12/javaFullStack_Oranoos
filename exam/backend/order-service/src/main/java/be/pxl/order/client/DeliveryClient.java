package be.pxl.order.client;

import be.pxl.order.dto.client.SlotAvailabilityResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "delivery-service", fallback = DeliveryClientFallback.class)
public interface DeliveryClient {

    @GetMapping("/api/delivery/slots/{id}/availability")
    SlotAvailabilityResponse checkSlot(@PathVariable("id") Long slotId);
}
