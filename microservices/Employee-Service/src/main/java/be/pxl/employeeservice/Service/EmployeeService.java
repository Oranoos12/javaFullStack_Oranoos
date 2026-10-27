package be.pxl.employeeservice.Service;

import be.pxl.employeeservice.Client.NotificationClient;
import be.pxl.employeeservice.Dto.EmployeeDto;
import be.pxl.employeeservice.Dto.EmployeeResponse;
import be.pxl.employeeservice.Dto.NotificationRequest;
import be.pxl.employeeservice.Exception.EmployeeNotFoundException;
import be.pxl.employeeservice.Model.Employee;
import be.pxl.employeeservice.Repository.EmployeeRepo;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepo employeeRepo;
    private final NotificationClient notificationClient;
    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    public EmployeeService(EmployeeRepo employeeRepo, NotificationClient notificationClient) {
        this.employeeRepo = employeeRepo;
        this.notificationClient = notificationClient;
    }

    public EmployeeResponse add(EmployeeDto request){
        log.info("Nieuwe werknemer ontvangen: {} {}", request.getFirstName(), request.getLastName());

        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setDepartmentId(request.getDepartmentId());
        employee.setOrganizationId(request.getOrganizationId());

        Employee employeeSaved = employeeRepo.save(employee);
        log.info("Werknemer opgeslagen met id {}", employeeSaved.getId());

        log.info("Notificatie versturen naar {}", employeeSaved.getEmail());
        notificationClient.sendNotification(new NotificationRequest(
                employeeSaved.getEmail(),
                "Welkom " + employeeSaved.getFirstName() + ", je bent toegevoegd als werknemer."));

        return new EmployeeResponse(
                employeeSaved.getId(),
                employeeSaved.getFirstName(),
                employeeSaved.getLastName(),
                employeeSaved.getEmail(),
                employeeSaved.getDepartmentId(),
                employeeSaved.getOrganizationId());
    }


    public List<EmployeeResponse> findAll(){
        log.info("Alle werknemers opvragen");
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
        log.info("Werknemer opvragen met id {}", id);
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
            log.info("Werknemers opvragen voor organisatie {}", organizationId);
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
        log.info("Werknemers opvragen voor departement {}", departmentId);

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
        log.info("Werknemer met id {} aanpassen", id);
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
        log.info("Werknemer met id {} verwijderen", id);
        if (!employeeRepo.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        employeeRepo.deleteById(id);
    }
}
