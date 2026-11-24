package be.pxl.delivery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "run_stops")
@Getter
@Setter
@NoArgsConstructor
public class RunStop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private String address;
    private boolean delivered;

    public RunStop(Long orderId, String address) {
        this.orderId = orderId;
        this.address = address;
    }
}