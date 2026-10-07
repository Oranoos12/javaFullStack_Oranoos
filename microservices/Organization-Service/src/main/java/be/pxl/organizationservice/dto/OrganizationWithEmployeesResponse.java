package be.pxl.organizationservice.dto;

import java.util.List;

public record OrganizationWithEmployeesResponse(Long id, String name, List<EmployeeDto> employees) {
}
