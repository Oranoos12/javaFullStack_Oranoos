package be.pxl.order.dto.client;

import java.util.List;

public record AvailabilityRequest(List<OrderItem> items) {
}
