package be.pxl.employeeservice.Controller;

import be.pxl.employeeservice.Dto.EmployeeDto;
import be.pxl.employeeservice.Dto.EmployeeResponse;
import be.pxl.employeeservice.Exception.EmployeeNotFoundException;
import be.pxl.employeeservice.Service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    private static final String VALID_JSON = """
            {
              "firstName": "Jan",
              "lastName": "Peeters",
              "email": "jan@pxl.be",
              "departmentId": 1,
              "organizationId": 2
            }
            """;

    private static final String INVALID_JSON = """
            {
              "firstName": "",
              "lastName": "Peeters",
              "email": "geen-email",
              "departmentId": 1,
              "organizationId": 2
            }
            """;

    @Test
    void create_validRequest_returns201() throws Exception {
        EmployeeResponse response = new EmployeeResponse(1L, "Jan", "Peeters", "jan@pxl.be",  1L, 2L);
        when(employeeService.add(any(EmployeeDto.class))).thenReturn(response);

        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(VALID_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Jan"));
    }

    @Test
    void create_invalidRequest_returns400AndNeverCallsService() throws Exception {
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(INVALID_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.email").exists());

        verify(employeeService, never()).add(any());
    }

    @Test
    void findAll_returns200() throws Exception {
        EmployeeResponse response = new EmployeeResponse(1L, "Jan", "Peeters", "jan@pxl.be",  1L, 2L);
        when(employeeService.findAll()).thenReturn(List.of(response));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName").value("Peeters"));
    }

    @Test
    void findById_unknownId_returns404() throws Exception {
        when(employeeService.findById(99L)).thenThrow(new EmployeeNotFoundException(99L));

        mockMvc.perform(get("/employees/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void delete_existingId_returns204() throws Exception {
        mockMvc.perform(delete("/employees/5"))
                .andExpect(status().isNoContent());

        verify(employeeService).delete(5L);
    }
    @Test
    void findByDepartment_returns200() throws Exception {
        EmployeeResponse response = new EmployeeResponse(1L, "Jan", "Peeters", "jan@pxl.be", 1L, 2L);
        when(employeeService.findByDepartment(1L)).thenReturn(List.of(response));

        mockMvc.perform(get("/employees/department/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Jan"))
                .andExpect(jsonPath("$[0].departmentId").value(1));

        verify(employeeService).findByDepartment(1L);
    }

    @Test
    void findByOrganization_returns200() throws Exception {
        EmployeeResponse response = new EmployeeResponse(1L, "Jan", "Peeters", "jan@pxl.be", 1L, 2L);
        when(employeeService.findByOrganization(2L)).thenReturn(List.of(response));

        mockMvc.perform(get("/employees/organization/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName").value("Peeters"))
                .andExpect(jsonPath("$[0].organizationId").value(2));

        verify(employeeService).findByOrganization(2L);
    }
}
