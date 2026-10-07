package be.pxl.notification.dto;

import jakarta.validation.constraints.NotBlank;

public record NotificationRequest(
        @NotBlank(message = "Ontvanger is verplicht") String recipient,
        @NotBlank(message = "Bericht is verplicht") String message
) {
}
