package be.pxl.inventory.dto.request;

import java.util.List;

public record AvailabilityRequest(List<OrderItem> items) {
}
