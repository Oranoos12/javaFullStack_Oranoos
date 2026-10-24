package be.pxl.organizationservice.client;

import be.pxl.organizationservice.dto.DepartmentDto;
import be.pxl.organizationservice.dto.DepartmentWithEmployeesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "department-service")
public interface DepartmentClient {

    @GetMapping("/departments/organization/{organizationId}")
    List<DepartmentDto> findByOrganization(@PathVariable("organizationId") Long organizationId);

    @GetMapping("/departments/organization/{organizationId}/with-employees")
    List<DepartmentWithEmployeesDto> findByOrganizationWithEmployees(@PathVariable("organizationId") Long organizationId);
}
