package be.pxl.notification.dto.event;

public record OrderCancelledEvent(Long orderId, String customerId, Long slotId) {
}