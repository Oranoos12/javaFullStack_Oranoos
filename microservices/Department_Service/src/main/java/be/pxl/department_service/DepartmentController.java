package be.pxl.department_service;

import be.pxl.department_service.Dto.DepartmentDtos;
import be.pxl.department_service.Dto.DepartmentResponse;
import jakarta.servlet.ServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private  final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentResponse> add(@Valid @RequestBody DepartmentDtos request){
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.add(request));
    }

    @GetMapping("/{id}")
    public DepartmentResponse findById(@PathVariable Long id){
        return departmentService.findById(id);
    }
    @GetMapping
    public List<DepartmentResponse> findAll() {
        return departmentService.findAll();
    }

    @GetMapping("/organization/{organizationId}")
    public List<DepartmentResponse> findByOrganization(@PathVariable Long organizationId) {
        return departmentService.findByOrganization(organizationId);
    }


}
