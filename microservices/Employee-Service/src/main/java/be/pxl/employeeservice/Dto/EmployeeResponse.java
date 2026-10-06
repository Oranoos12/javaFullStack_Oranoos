package be.pxl.employeeservice.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class EmployeeResponse {

    private  Long id;
    private  String firstName;
    private  String lastName;
    private String Email;
    private  Long departmentId;
    private  Long organizationId;

}
