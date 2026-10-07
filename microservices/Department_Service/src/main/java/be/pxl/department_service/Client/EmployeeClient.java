package be.pxl.department_service.Client;

import be.pxl.department_service.Dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class EmployeeClient {
    private final RestClient restClient;

    public EmployeeClient(@Value("${employee-service.url}") String employeeServiceUrl) {
        this.restClient = RestClient.create(employeeServiceUrl);
    }

    public List<EmployeeDto> findByDepartment(Long departmentId) {
        return restClient.get()
                .uri("/employees/department/{departmentId}", departmentId)
                .retrieve()
                .body(new ParameterizedTypeReference<List<EmployeeDto>>() {});
    }
}
