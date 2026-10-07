package be.pxl.department_service.Service;


import be.pxl.department_service.Client.EmployeeClient;
import be.pxl.department_service.Dto.DepartmentDtos;
import be.pxl.department_service.Dto.DepartmentResponse;
import be.pxl.department_service.Dto.DepartmentWithEmployeesResponse;
import be.pxl.department_service.Dto.EmployeeDto;
import be.pxl.department_service.Exception.DepartmentNotFoundException;
import be.pxl.department_service.Model.Department;
import be.pxl.department_service.Repository.DepartmentRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepo repository;
    private final EmployeeClient employeeClient;

    public DepartmentService(DepartmentRepo repository, EmployeeClient employeeClient) {
        this.repository = repository;
        this.employeeClient = employeeClient;
    }

    public DepartmentResponse add(DepartmentDtos req) {
        Department department = new Department();
        department.setName(req.getName());
        department.setOrganizationId(req.getOrganizationId());

        Department saved = repository.save(department);

        return new DepartmentResponse(
                saved.getId(),
                saved.getName(),
                saved.getOrganizationId()
        );
    }

    public DepartmentResponse findById(Long id) {
        Department department = repository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));

        return new DepartmentResponse(
                department.getId(),
                department.getName(),
                department.getOrganizationId()
        );
    }

    public List<DepartmentResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(d -> new DepartmentResponse(
                        d.getId(),
                        d.getName(),
                        d.getOrganizationId()
                ))
                .toList();
    }

    public List<DepartmentResponse> findByOrganization(Long organizationId) {
        return repository.findByOrganizationId(organizationId)
                .stream()
                .map(d -> new DepartmentResponse(
                        d.getId(),
                        d.getName(),
                        d.getOrganizationId()
                ))
                .toList();
    }


    public List<DepartmentWithEmployeesResponse> findByOrganizationWithEmployees(Long organizationId) {
        List<DepartmentWithEmployeesResponse> result = new ArrayList<>();

        for (Department department : repository.findByOrganizationId(organizationId)) {
            List<EmployeeDto> employees = employeeClient.findByDepartment(department.getId());
            result.add(new DepartmentWithEmployeesResponse(
                    department.getId(),
                    department.getName(),
                    department.getOrganizationId(),
                    employees));
        }
        return result;
    }


}
