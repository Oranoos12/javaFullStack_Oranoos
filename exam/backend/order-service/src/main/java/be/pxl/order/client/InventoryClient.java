package be.pxl.order.client;

import be.pxl.order.dto.client.AvailabilityRequest;
import be.pxl.order.dto.client.AvailabilityResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service", fallback = InventoryClientFallback.class)
public interface InventoryClient {

    @PostMapping("/api/inventory/availability")
    AvailabilityResponse checkAvailability(@RequestBody AvailabilityRequest request);
}
