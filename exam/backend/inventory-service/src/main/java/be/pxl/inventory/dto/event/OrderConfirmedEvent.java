package be.pxl.inventory.dto.event;

import be.pxl.inventory.dto.request.OrderItem;

import java.util.List;

public record OrderConfirmedEvent(
        Long orderId,
        String customerId,
        Long slotId,
        String address,
        int persons,
        List<OrderItem> items
) {
}
