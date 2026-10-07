package be.pxl.department_service.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DepartmentDtos {

    @NotBlank(message = "Name is verplicht")
    private String name;
    @NotNull(message = "OrganizationId is verplicht")
    private  Long organizationId;

}
