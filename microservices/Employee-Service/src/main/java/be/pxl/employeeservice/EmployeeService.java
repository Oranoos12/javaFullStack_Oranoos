package be.pxl.employeeservice;

import be.pxl.employeeservice.Dto.EmployeeDto;
import be.pxl.employeeservice.Dto.EmployeeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmployeeService {
    private  final  EmployeeRepo employeeRepo;

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public EmployeeResponse add(EmployeeDto request){

        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(employee.getEmail());
        employee.setDepartmentId(employee.getDepartmentId());
        employee.setOrganizationId(employee.getOrganizationId());


        Employee employeeSaved = employeeRepo.save(employee);

        return new EmployeeResponse(employeeSaved.getFirstName(),
                employeeSaved.getLastName(),
                employeeSaved.getEmail(),
                employeeSaved.getDepartmentId(),
                employeeSaved.getOrganizationId());

    }


    public List<EmployeeResponse> findAll(){
        return employeeRepo.findAll().stream().map(employee -> new EmployeeResponse(
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartmentId(),
                employee.getOrganizationId()
        )).toList();

    }



    public  EmployeeResponse findById(Long id){
        Employee employee  = employeeRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee " + id + " niet gevonden"));


        return  new EmployeeResponse(
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartmentId(),
                employee.getOrganizationId()
        );
    }


        public List<EmployeeResponse> findByOrganization(Long organizationId) {
            return employeeRepo.findByOrganizationId(organizationId)
                    .stream()
                    .map(employee-> new EmployeeResponse(
                            employee.getFirstName(),
                            employee.getLastName(),
                            employee.getEmail(),
                            employee.getDepartmentId(),
                            employee.getOrganizationId()
                    ))
                    .toList();
        }


    public  List<EmployeeResponse> findByDepartment(Long departmentId){

        return employeeRepo.findByDepartmentId(departmentId)
                .stream()
                .map(employee-> new EmployeeResponse(
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getEmail(),
                        employee.getDepartmentId(),
                        employee.getOrganizationId()
                ))
                .toList();

    }
}
