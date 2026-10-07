package be.pxl.organizationservice.dto;

import java.util.List;

public record OrganizationWithDepartmentsResponse(Long id, String name, List<DepartmentDto> departments) {
}
