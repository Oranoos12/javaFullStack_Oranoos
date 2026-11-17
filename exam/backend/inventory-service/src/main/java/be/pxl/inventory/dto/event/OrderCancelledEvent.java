package be.pxl.inventory.dto.event;

public record OrderCancelledEvent(Long orderId, String customerId, Long slotId) {
}
