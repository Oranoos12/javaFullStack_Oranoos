package be.pxl.order.controller;

import be.pxl.order.dto.request.OrderRequest;
import be.pxl.order.dto.response.OrderResponse;
import be.pxl.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse placeOrder(@RequestHeader(value = "X-User-Role", required = false) String role,
                                    @RequestHeader(value = "X-User-Id", required = false) String userId,
                                    @Valid @RequestBody OrderRequest request) {
        return orderService.placeOrder(role, userId, request);
    }

    @GetMapping
    public List<OrderResponse> findMyOrders(@RequestHeader(value = "X-User-Role", required = false) String role,
                                            @RequestHeader(value = "X-User-Id", required = false) String userId) {
        return orderService.findMyOrders(role, userId);
    }

    @GetMapping("/{id}")
    public OrderResponse findById(@RequestHeader(value = "X-User-Role", required = false) String role,
                                  @RequestHeader(value = "X-User-Id", required = false) String userId,
                                  @PathVariable Long id) {
        return orderService.findById(role, userId, id);
    }

    @PostMapping("/{id}/cancel")
    public OrderResponse cancel(@RequestHeader(value = "X-User-Role", required = false) String role,
                                @RequestHeader(value = "X-User-Id", required = false) String userId,
                                @PathVariable Long id) {
        return orderService.cancel(role, userId, id);
    }
}