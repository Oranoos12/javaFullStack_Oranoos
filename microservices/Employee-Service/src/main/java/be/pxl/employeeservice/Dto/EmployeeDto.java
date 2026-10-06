package be.pxl.employeeservice.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeDto {

    @NotBlank(message = "First name is verplicht")
    private  String firstName;
    @NotBlank(message = "Last name is verplicht")
    private  String lastName;
    @Email(message = "Ongeldig emailadres")
    @NotBlank(message = "Email is verplicht")
    private String email;
    @NotNull(message = "DepartmentId is verplicht")
    private  Long departmentId;
    @NotNull(message = "OrganizationId is verplicht")
    private  Long organizationId;

}
