package be.pxl.organizationservice.dto;

import java.util.List;

public record OrganizationWithDepartmentsAndEmployeesResponse(Long id, String name, List<DepartmentWithEmployeesDto> departments) {
}
