package be.pxl.order.dto.response;

import be.pxl.order.dto.request.AddressDto;
import be.pxl.order.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        String customerId,
        int persons,
        Long slotId,
        AddressDto address,
        OrderStatus status,
        LocalDateTime createdAt,
        List<OrderLineResponse> lines
) {
}
