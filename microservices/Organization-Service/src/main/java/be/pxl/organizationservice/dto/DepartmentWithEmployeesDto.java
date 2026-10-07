package be.pxl.organizationservice.dto;

import java.util.List;

public record DepartmentWithEmployeesDto(Long id, String name, List<EmployeeDto> employees) {
}
