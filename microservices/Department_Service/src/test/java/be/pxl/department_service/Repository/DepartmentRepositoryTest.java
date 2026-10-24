package be.pxl.department_service.Repository;

import be.pxl.department_service.Model.Department;
import org.junit.jupiter.api.BeforeEach;
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

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class DepartmentRepositoryTest {

    @Container
    @ServiceConnection
    static MySQLContainer mysql = new MySQLContainer("mysql:8.4");

    @Autowired
    private DepartmentRepo departmentRepo;

    @BeforeEach
    void cleanDatabase() {
        departmentRepo.deleteAll();
    }

    private Department createDepartment(String name, Long organizationId) {
        Department department = new Department();
        department.setName(name);
        department.setOrganizationId(organizationId);
        return department;
    }

    @Test
    void savesAndFindsDepartment() {
        Department saved = departmentRepo.save(createDepartment("IT", 1L));

        Department found = departmentRepo.findById(saved.getId()).orElseThrow();
        assertEquals("IT", found.getName());
        assertEquals(1L, found.getOrganizationId());
    }

    @Test
    void findByOrganizationId_returnsOnlyThatOrganization() {
        departmentRepo.save(createDepartment("IT", 1L));
        departmentRepo.save(createDepartment("HR", 1L));
        departmentRepo.save(createDepartment("Sales", 2L));

        List<Department> result = departmentRepo.findByOrganizationId(1L);

        assertEquals(2, result.size());
    }
}