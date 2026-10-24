package be.pxl.department_service.Client;

import be.pxl.department_service.Dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "employee-service")
public interface EmployeeClient {

    @GetMapping("/employees/department/{departmentId}")
    List<EmployeeDto> findByDepartment(@PathVariable("departmentId") Long departmentId);
}
