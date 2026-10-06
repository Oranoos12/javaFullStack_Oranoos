package be.pxl.department_service.Exception;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(Long id){
        super("Employee met id " + id + " bestaat niet.");
    }
}
