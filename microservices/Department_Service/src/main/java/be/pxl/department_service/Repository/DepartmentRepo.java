package be.pxl.department_service.Repository;

import be.pxl.department_service.Model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {
    List<Department> findByOrganizationId(Long organizationId);

}
