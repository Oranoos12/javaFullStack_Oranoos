package be.pxl.department_service;


import be.pxl.department_service.Dto.DepartmentDtos;
import be.pxl.department_service.Dto.DepartmentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepo repository;

    public DepartmentService(DepartmentRepo repository) {
        this.repository = repository;
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department " + id + " niet gevonden"));

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


}
