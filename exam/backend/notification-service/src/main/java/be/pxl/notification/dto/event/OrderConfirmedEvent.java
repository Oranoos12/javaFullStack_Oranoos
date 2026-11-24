package be.pxl.notification.dto.event;

public record OrderConfirmedEvent(Long orderId, String customerId, Long slotId, String address, int persons) {
}