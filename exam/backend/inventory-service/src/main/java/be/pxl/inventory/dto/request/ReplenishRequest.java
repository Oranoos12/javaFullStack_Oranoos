package be.pxl.inventory.dto.request;

import jakarta.validation.constraints.Positive;

public record ReplenishRequest(@Positive(message = "Hoeveelheid moet groter zijn dan 0") double quantity) {
}
