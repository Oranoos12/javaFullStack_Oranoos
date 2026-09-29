package be.pxl.employeeservice;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    List<Employee> findByOrganizationId(Long organizationId);
    List<Employee> findByDepartmentId(Long departmentId);
}
