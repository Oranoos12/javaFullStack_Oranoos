package be.pxl.delivery.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record SlotResponse(
        Long id,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        int capacity,
        int booked,
        int freePlaces
) {
}