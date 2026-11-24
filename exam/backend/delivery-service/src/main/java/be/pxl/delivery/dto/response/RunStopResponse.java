package be.pxl.delivery.dto.response;

public record RunStopResponse(Long orderId, String address, boolean delivered) {
}
