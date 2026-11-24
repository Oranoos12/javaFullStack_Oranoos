package be.pxl.delivery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "delivery_slots")
@Getter
@Setter
@NoArgsConstructor
public class DeliverySlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int capacity;
    private int booked;

    public DeliverySlot(LocalDate date, LocalTime startTime, LocalTime endTime, int capacity) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
    }

    public boolean hasRoom() {
        return booked < capacity;
    }

    public void book() {
        if (hasRoom()) {
            booked = booked + 1;
        }
    }

    public void release() {
        if (booked > 0) {
            booked = booked - 1;
        }
    }

    public int freePlaces() {
        return capacity - booked;
    }
}