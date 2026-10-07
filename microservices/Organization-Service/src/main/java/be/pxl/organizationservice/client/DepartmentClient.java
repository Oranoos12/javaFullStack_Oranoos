package be.pxl.organizationservice.client;

import org.springframework.stereotype.Component;
import be.pxl.organizationservice.dto.DepartmentDto;
import be.pxl.organizationservice.dto.DepartmentWithEmployeesDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class DepartmentClient {
    private final RestClient restClient;

    public DepartmentClient(@Value("${department-service.url}") String departmentServiceUrl) {
        this.restClient = RestClient.create(departmentServiceUrl);
    }

    public List<DepartmentDto> findByOrganization(Long organizationId) {
        return restClient.get()
                .uri("/departments/organization/{organizationId}", organizationId)
                .retrieve()
                .body(new ParameterizedTypeReference<List<DepartmentDto>>() {});
    }

    public List<DepartmentWithEmployeesDto> findByOrganizationWithEmployees(Long organizationId) {
        return restClient.get()
                .uri("/departments/organization/{organizationId}/with-employees", organizationId)
                .retrieve()
                .body(new ParameterizedTypeReference<List<DepartmentWithEmployeesDto>>() {});
    }
}
