package be.pxl.employeeservice;


import be.pxl.employeeservice.Dto.EmployeeDto;
import be.pxl.employeeservice.Dto.EmployeeResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class EmployeeController {

    private  final  EmployeeService employeeService
            ;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> add(@Valid @RequestBody EmployeeDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.add(request));
    }

    @GetMapping
    public List<EmployeeResponse> findAll(){
        return  employeeService.findAll();
    }

    @GetMapping("/{id}")
    public  EmployeeResponse findById(@PathVariable Long id){
        return  employeeService.findById(id);
    }

    @GetMapping("/department/{departmentid}")
    public  List<EmployeeResponse> findByDepartmentId(@PathVariable Long departmentid){
        return employeeService.findByDepartment(departmentid);
    }
    @GetMapping("/organization/{organizationId}")
    public  List<EmployeeResponse> findByOrganizationId(@PathVariable Long organizationId){
        return employeeService.findByOrganization(organizationId);
    }
}
