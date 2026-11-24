package be.pxl.delivery.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "delivery_runs")
@Getter
@Setter
@NoArgsConstructor
public class DeliveryRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long slotId;
    private String driverId;

    @Enumerated(EnumType.STRING)
    private RunStatus status = RunStatus.PLANNED;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "run_id")
    private List<RunStop> stops = new ArrayList<>();

    public DeliveryRun(Long slotId, String driverId) {
        this.slotId = slotId;
        this.driverId = driverId;
    }

    public void addStop(Long orderId, String address) {
        stops.add(new RunStop(orderId, address));
        if (status == RunStatus.DONE) {
            status = RunStatus.PLANNED;
        }
    }

    public void removeStop(Long orderId) {
        RunStop toRemove = null;
        for (RunStop stop : stops) {
            if (stop.getOrderId().equals(orderId)) {
                toRemove = stop;
            }
        }
        if (toRemove != null) {
            stops.remove(toRemove);
        }
    }

    public RunStop findStop(Long orderId) {
        for (RunStop stop : stops) {
            if (stop.getOrderId().equals(orderId)) {
                return stop;
            }
        }
        return null;
    }

    public void start() {
        if (status == RunStatus.PLANNED) {
            status = RunStatus.IN_PROGRESS;
        }
    }

    public void markDelivered(Long orderId) {
        RunStop stop = findStop(orderId);
        if (stop != null) {
            stop.setDelivered(true);
        }
        if (allDelivered()) {
            status = RunStatus.DONE;
        }
    }

    public boolean allDelivered() {
        for (RunStop stop : stops) {
            if (!stop.isDelivered()) {
                return false;
            }
        }
        return true;
    }

    public List<Long> openOrderIds() {
        List<Long> result = new ArrayList<>();
        for (RunStop stop : stops) {
            if (!stop.isDelivered()) {
                result.add(stop.getOrderId());
            }
        }
        return result;
    }
}