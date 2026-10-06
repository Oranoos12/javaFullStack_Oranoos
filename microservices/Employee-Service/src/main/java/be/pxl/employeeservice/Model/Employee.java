package be.pxl.employeeservice.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String  firstName;
    private  String lastName;
    private String Email;
    private  Long departmentId;
    private  Long organizationId;

    public Employee() {

    }
}
