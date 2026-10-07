package be.pxl.department_service.Controller;

import be.pxl.department_service.Dto.DepartmentDtos;
import be.pxl.department_service.Dto.DepartmentResponse;
import be.pxl.department_service.Dto.DepartmentWithEmployeesResponse;
import be.pxl.department_service.Dto.EmployeeDto;
import be.pxl.department_service.Exception.DepartmentNotFoundException;
import be.pxl.department_service.Service.DepartmentService;
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

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private DepartmentService departmentService;

    private static final String VALID_JSON = """
            {
              "name": "IT",
              "organizationId": 1
            }
            """;

    private static final String INVALID_JSON = """
            {
              "name": ""
            }
            """;

    @Test
    void create_validRequest_returns201() throws Exception {
        when(departmentService.add(any(DepartmentDtos.class))).thenReturn(new DepartmentResponse(1L, "IT", 1L));

        mockMvc.perform(post("/departments").contentType(MediaType.APPLICATION_JSON).content(VALID_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("IT"));
    }

    @Test
    void findAll_returns200() throws Exception {
        when(departmentService.findAll()).thenReturn(List.of(new DepartmentResponse(1L, "IT", 1L)));

        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("IT"));
    }

    @Test
    void findById_unknownId_returns404() throws Exception {
        when(departmentService.findById(99L)).thenThrow(new DepartmentNotFoundException(99L));

        mockMvc.perform(get("/departments/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void findByOrganizationWithEmployees_returns200() throws Exception {
        EmployeeDto employee = new EmployeeDto(7L, "Jan", "Peeters", "jan@pxl.be");
        when(departmentService.findByOrganizationWithEmployees(1L))
                .thenReturn(List.of(new DepartmentWithEmployeesResponse(1L, "IT", 1L, List.of(employee))));

        mockMvc.perform(get("/departments/organization/1/with-employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("IT"))
                .andExpect(jsonPath("$[0].employees[0].firstName").value("Jan"));
    }


}
