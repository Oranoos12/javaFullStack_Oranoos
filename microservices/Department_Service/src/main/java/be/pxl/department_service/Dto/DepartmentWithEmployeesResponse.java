package be.pxl.department_service.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DepartmentWithEmployeesResponse {
    private Long id;
    private String name;
    private Long organizationId;
    private List<EmployeeDto> employees;
}
