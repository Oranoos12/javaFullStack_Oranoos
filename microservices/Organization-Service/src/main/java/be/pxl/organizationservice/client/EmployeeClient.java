package be.pxl.organizationservice.client;

import be.pxl.organizationservice.dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "employee-service")
public interface EmployeeClient {

    @GetMapping("/employees/organization/{organizationId}")
    List<EmployeeDto> findByOrganization(@PathVariable("organizationId") Long organizationId);
}
