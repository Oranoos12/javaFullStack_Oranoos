package be.pxl.order.service;

import be.pxl.order.dto.request.AddressDto;
import be.pxl.order.dto.response.OrderLineResponse;
import be.pxl.order.dto.request.OrderRequest;
import be.pxl.order.dto.response.OrderResponse;
import be.pxl.order.exception.ForbiddenException;
import be.pxl.order.exception.OrderNotFoundException;
import be.pxl.order.model.Address;
import be.pxl.order.model.Order;
import be.pxl.order.model.OrderLine;
import be.pxl.order.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderResponse placeOrder(String role, String customerId, OrderRequest request) {
        checkKlant(role, customerId);

        log.info("Nieuwe bestelling van klant {} voor {} personen",
                customerId, request.persons());

        Order order = new Order();
        order.setCustomerId(customerId);
        order.setPersons(request.persons());
        order.setSlotId(request.slotId());
        order.setDeliveryAddress(toAddress(request.address()));

        order.confirm();

        Order saved = orderRepository.save(order);

        log.info("Bestelling {} bevestigd", saved.getId());

        return toResponse(saved);
    }

    public List<OrderResponse> findMyOrders(String role, String customerId) {
        checkKlant(role, customerId);

        List<OrderResponse> result = new ArrayList<>();

        for (Order order : orderRepository.findByCustomerIdOrderByCreatedAtDesc(customerId)) {
            result.add(toResponse(order));
        }

        return result;
    }

    public OrderResponse findById(String role, String customerId, Long id) {
        checkKlant(role, customerId);

        return toResponse(getOwnOrder(customerId, id));
    }

    public OrderResponse cancel(String role, String customerId, Long id) {
        checkKlant(role, customerId);

        Order order = getOwnOrder(customerId, id);

        order.cancel();

        Order saved = orderRepository.save(order);

        log.info("Bestelling {} geannuleerd", saved.getId());

        return toResponse(saved);
    }

    private void checkKlant(String role, String customerId) {
        if (!"KLANT".equals(role) || customerId == null || customerId.isBlank()) {
            throw new ForbiddenException(
                    "Enkel een ingelogde KLANT mag bestellingen beheren."
            );
        }
    }

    private Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    private Order getOwnOrder(String customerId, Long id) {
        Order order = getOrder(id);

        if (!order.getCustomerId().equals(customerId)) {
            throw new ForbiddenException(
                    "Deze bestelling hoort niet bij jou."
            );
        }

        return order;
    }

    private Address toAddress(AddressDto dto) {
        return new Address(
                dto.street(),
                dto.number(),
                dto.postalCode(),
                dto.city()
        );
    }

    private OrderResponse toResponse(Order order) {

        List<OrderLineResponse> lines = new ArrayList<>();

        for (OrderLine line : order.getLines()) {
            lines.add(
                    new OrderLineResponse(
                            line.getRecipeId(),
                            line.getRecipeName()
                    )
            );
        }

        Address address = order.getDeliveryAddress();

        AddressDto addressDto = new AddressDto(
                address.getStreet(),
                address.getNumber(),
                address.getPostalCode(),
                address.getCity()
        );

        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getPersons(),
                order.getSlotId(),
                addressDto,
                order.getStatus(),
                order.getCreatedAt(),
                lines
        );
    }
}