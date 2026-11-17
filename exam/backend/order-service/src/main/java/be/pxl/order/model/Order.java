package be.pxl.order.model;

import be.pxl.order.exception.OrderNotCancellableException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerId;
    private int persons;
    private Long slotId;

    @Embedded
    private Address deliveryAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PLACED;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private List<OrderLine> lines = new ArrayList<>();

    public void confirm() {
        if (status == OrderStatus.PLACED) {
            status = OrderStatus.CONFIRMED;
        }
    }

    public void cancel() {
        if (status != OrderStatus.PLACED && status != OrderStatus.CONFIRMED) {
            throw new OrderNotCancellableException(id, status);
        }
        status = OrderStatus.CANCELLED;
    }

    public void markInDelivery() {
        if (status == OrderStatus.CONFIRMED) {
            status = OrderStatus.IN_DELIVERY;
        }
    }

    public void markDelivered() {
        if (status == OrderStatus.CONFIRMED || status == OrderStatus.IN_DELIVERY) {
            status = OrderStatus.DELIVERED;
        }
    }
}
