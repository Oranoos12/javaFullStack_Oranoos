package be.pxl.employeeservice.Service;


import be.pxl.employeeservice.Dto.EmployeeDto;
import be.pxl.employeeservice.Dto.EmployeeResponse;
import be.pxl.employeeservice.Exception.EmployeeNotFoundException;
import be.pxl.employeeservice.Model.Employee;
import be.pxl.employeeservice.Repository.EmployeeRepo;
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
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepo employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee createEmployee(Long id) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName("Jan");
        employee.setLastName("Peeters");
        employee.setEmail("jan@pxl.be");
        employee.setDepartmentId(1L);
        employee.setOrganizationId(2L);
        return employee;
    }

    @Test
    void create_savesEmployeeWithAllFields() {
        EmployeeDto request = new EmployeeDto("Jan", "Peeters", "jan@pxl.be", 1L, 2L);
        when(employeeRepository.save(any(Employee.class))).thenReturn(createEmployee(10L));

        EmployeeResponse response = employeeService.add(request);

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(captor.capture());
        Employee saved = captor.getValue();
        assertEquals("Jan", saved.getFirstName());
        assertEquals("Peeters", saved.getLastName());
        assertEquals("jan@pxl.be", saved.getEmail());
        assertEquals(1L, saved.getDepartmentId());
        assertEquals(2L, saved.getOrganizationId());
        assertEquals(10L, response.getId());
    }

    @Test
    void findById_existingEmployee_returnsResponse() {
        when(employeeRepository.findById(5L)).thenReturn(Optional.of(createEmployee(5L)));

        EmployeeResponse response = employeeService.findById(5L);

        assertEquals(5L, response.getId());
        assertEquals("Jan", response.getFirstName());
    }

    @Test
    void findById_unknownEmployee_throwsNotFound() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findById(99L));
    }

    @Test
    void findByDepartment_returnsEmployeesOfThatDepartment() {
        when(employeeRepository.findByDepartmentId(1L)).thenReturn(List.of(createEmployee(1L), createEmployee(2L)));

        List<EmployeeResponse> result = employeeService.findByDepartment(1L);

        assertEquals(2, result.size());
    }

    @Test
    void update_existingEmployee_savesNewValues() {
        Employee existing = createEmployee(5L);
        when(employeeRepository.findById(5L)).thenReturn(Optional.of(existing));
        when(employeeRepository.save(any(Employee.class))).thenReturn(existing);
        EmployeeDto request = new EmployeeDto("Piet", "Janssens", "piet@pxl.be", 3L, 4L);

        employeeService.update(5L, request);

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(captor.capture());
        assertEquals("Piet", captor.getValue().getFirstName());
        assertEquals(3L, captor.getValue().getDepartmentId());
    }

    @Test
    void update_unknownEmployee_neverSaves() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());
        EmployeeDto request = new EmployeeDto("Piet", "Janssens", "piet@pxl.be", 3L, 4L);

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.update(99L, request));

        verify(employeeRepository, never()).save(any());
    }

    @Test
    void delete_existingEmployee_deletesIt() {
        when(employeeRepository.existsById(5L)).thenReturn(true);

        employeeService.delete(5L);

        verify(employeeRepository).deleteById(5L);
    }

    @Test
    void delete_unknownEmployee_neverDeletes() {
        when(employeeRepository.existsById(99L)).thenReturn(false);

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.delete(99L));

        verify(employeeRepository, never()).deleteById(any());
    }
}
