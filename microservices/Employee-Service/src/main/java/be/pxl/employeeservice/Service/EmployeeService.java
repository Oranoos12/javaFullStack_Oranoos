package be.pxl.employeeservice.Service;

import be.pxl.employeeservice.Dto.EmployeeDto;
import be.pxl.employeeservice.Dto.EmployeeResponse;
import be.pxl.employeeservice.Exception.EmployeeNotFoundException;
import be.pxl.employeeservice.Model.Employee;
import be.pxl.employeeservice.Repository.EmployeeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private  final EmployeeRepo employeeRepo;

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public EmployeeResponse add(EmployeeDto request){

        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setDepartmentId(request.getDepartmentId());
        employee.setOrganizationId(request.getOrganizationId());


        Employee employeeSaved = employeeRepo.save(employee);

        return new EmployeeResponse(
                employeeSaved.getId(),
                employeeSaved.getFirstName(),
                employeeSaved.getLastName(),
                employeeSaved.getEmail(),
                employeeSaved.getDepartmentId(),
                employeeSaved.getOrganizationId());

    }


    public List<EmployeeResponse> findAll(){
        return employeeRepo.findAll().stream().map(employee -> new EmployeeResponse(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartmentId(),
                employee.getOrganizationId()
        )).toList();

    }



    public  EmployeeResponse findById(Long id){
        Employee employee  = employeeRepo.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));


        return  new EmployeeResponse(
                employee.getId(),
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
                            employee.getId(),
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
                        employee.getId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getEmail(),
                        employee.getDepartmentId(),
                        employee.getOrganizationId()
                ))
                .toList();

    }


    public EmployeeResponse update(Long id, EmployeeDto request) {
        Employee employee = employeeRepo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setDepartmentId(request.getDepartmentId());
        employee.setOrganizationId(request.getOrganizationId());

        Employee newEmployee = employeeRepo.save(employee);

        return new EmployeeResponse(
                newEmployee.getId(),
                newEmployee .getFirstName(),
                newEmployee .getLastName(),
                newEmployee .getEmail(),
                newEmployee .getDepartmentId(),
                newEmployee .getOrganizationId());
    }

    public void delete(Long id) {
        if (!employeeRepo.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        employeeRepo.deleteById(id);
    }
}
