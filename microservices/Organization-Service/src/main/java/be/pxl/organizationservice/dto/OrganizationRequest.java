package be.pxl.organizationservice.dto;

import jakarta.validation.constraints.NotBlank;

public record OrganizationRequest(@NotBlank(message = "Naam is verplicht") String name) {
}
