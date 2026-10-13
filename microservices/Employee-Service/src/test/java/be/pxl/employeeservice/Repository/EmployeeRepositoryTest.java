package be.pxl.employeeservice;

import be.pxl.employeeservice.Model.Employee;
import be.pxl.employeeservice.Repository.EmployeeRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class EmployeeRepositoryTest {

    @Container
    @ServiceConnection
    static MySQLContainer mysql = new MySQLContainer("mysql:8.4");

    @Autowired
    private EmployeeRepo employeeRepo;

    private Employee createEmployee(String firstName, Long departmentId, Long organizationId) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName("Peeters");
        employee.setEmail(firstName + "@pxl.be");
        employee.setDepartmentId(departmentId);
        employee.setOrganizationId(organizationId);
        return employee;
    }

    @Test
    void savesAndFindsEmployee() {
        Employee saved = employeeRepo.save(createEmployee("Jan", 1L, 2L));

        assertNotNull(saved.getId());
        Employee found = employeeRepo.findById(saved.getId()).orElseThrow();
        assertEquals("Jan", found.getFirstName());
        assertEquals(1L, found.getDepartmentId());
        assertEquals(2L, found.getOrganizationId());
    }

    @Test
    void findByDepartmentId_returnsOnlyThatDepartment() {
        employeeRepo.save(createEmployee("Piet", 100L, 5L));
        employeeRepo.save(createEmployee("Els", 100L, 5L));
        employeeRepo.save(createEmployee("Sara", 200L, 5L));

        List<Employee> result = employeeRepo.findByDepartmentId(100L);

        assertEquals(2, result.size());
    }

    @Test
    void findByOrganizationId_returnsOnlyThatOrganization() {
        employeeRepo.save(createEmployee("Tom", 1L, 300L));
        employeeRepo.save(createEmployee("Lien", 1L, 400L));

        List<Employee> result = employeeRepo.findByOrganizationId(300L);

        assertEquals(1, result.size());
        assertEquals("Tom", result.get(0).getFirstName());
    }
}