package be.pxl.employeeservice.Exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(Long id){
        super("Employee met id " + id + " bestaat niet.");
    }
}
