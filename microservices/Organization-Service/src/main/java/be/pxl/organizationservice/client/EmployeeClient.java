package be.pxl.organizationservice.client;

import be.pxl.organizationservice.dto.EmployeeDto;
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

    public List<EmployeeDto> findByOrganization(Long organizationId) {
        return restClient.get()
                .uri("/employees/organization/{organizationId}", organizationId)
                .retrieve()
                .body(new ParameterizedTypeReference<List<EmployeeDto>>() {});
    }
}