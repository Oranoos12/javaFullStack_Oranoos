package be.pxl.department_service.Service;

import be.pxl.department_service.Client.EmployeeClient;
import be.pxl.department_service.Dto.DepartmentDtos;
import be.pxl.department_service.Dto.DepartmentResponse;
import be.pxl.department_service.Dto.DepartmentWithEmployeesResponse;
import be.pxl.department_service.Dto.EmployeeDto;
import be.pxl.department_service.Exception.DepartmentNotFoundException;
import be.pxl.department_service.Model.Department;
import be.pxl.department_service.Repository.DepartmentRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    @Mock
    private DepartmentRepo departmentRepository;

    @Mock
    private EmployeeClient employeeClient;

    @InjectMocks
    private DepartmentService departmentService;

    private Department createDepartment(Long id) {
        Department department = new Department();
        department.setId(id);
        department.setName("IT");
        department.setOrganizationId(1L);
        return department;
    }

    @Test
    void create_savesDepartmentWithAllFields() {
        when(departmentRepository.save(any(Department.class))).thenReturn(createDepartment(10L));

        DepartmentResponse response = departmentService.add(new DepartmentDtos("IT", 1L));

        ArgumentCaptor<Department> captor = ArgumentCaptor.forClass(Department.class);
        verify(departmentRepository).save(captor.capture());
        assertEquals("IT", captor.getValue().getName());
        assertEquals(1L, captor.getValue().getOrganizationId());
        assertEquals(10L, response.getId());
    }

    @Test
    void findById_existingDepartment_returnsResponse() {
        when(departmentRepository.findById(5L)).thenReturn(Optional.of(createDepartment(5L)));

        DepartmentResponse response = departmentService.findById(5L);

        assertEquals(5L, response.getId());
        assertEquals("IT", response.getName());
    }

    @Test
    void findById_unknownDepartment_throwsNotFound() {
        when(departmentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(DepartmentNotFoundException.class, () -> departmentService.findById(99L));
    }

    @Test
    void findByOrganization_returnsDepartmentsOfThatOrganization() {
        when(departmentRepository.findByOrganizationId(1L)).thenReturn(List.of(createDepartment(1L), createDepartment(2L)));

        List<DepartmentResponse> result = departmentService.findByOrganization(1L);

        assertEquals(2, result.size());
    }


    @Test
    void findByOrganizationWithEmployees_addsEmployeesToEachDepartment() {
        when(departmentRepository.findByOrganizationId(1L)).thenReturn(List.of(createDepartment(1L), createDepartment(2L)));
        when(employeeClient.findByDepartment(1L)).thenReturn(List.of(new EmployeeDto(7L, "Jan", "Peeters", "jan@pxl.be")));
        when(employeeClient.findByDepartment(2L)).thenReturn(List.of());

        List<DepartmentWithEmployeesResponse> result = departmentService.findByOrganizationWithEmployees(1L);

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getEmployees().size());
        assertEquals("Jan", result.get(0).getEmployees().get(0).getFirstName());
        assertEquals(0, result.get(1).getEmployees().size());
        verify(employeeClient).findByDepartment(1L);
        verify(employeeClient).findByDepartment(2L);
    }



}
