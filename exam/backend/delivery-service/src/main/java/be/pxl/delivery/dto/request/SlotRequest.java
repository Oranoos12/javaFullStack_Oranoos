package be.pxl.delivery.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;

public record SlotRequest(
        @NotNull(message = "Datum is verplicht")
        LocalDate date,
        @NotNull(message = "Beginuur is verplicht")
        LocalTime startTime,
        @NotNull(message = "Einduur is verplicht")
        LocalTime endTime,
        @Positive(message = "Capaciteit moet groter zijn dan 0")
        int capacity
) {
}
