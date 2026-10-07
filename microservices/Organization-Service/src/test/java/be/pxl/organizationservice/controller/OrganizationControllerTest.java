package be.pxl.organizationservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import be.pxl.organizationservice.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import be.pxl.organizationservice.exception.OrganizationNotFoundException;
import be.pxl.organizationservice.service.OrganizationService;

@WebMvcTest(OrganizationController.class)
class OrganizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrganizationService organizationService;

    @Test
    void create_validRequest_returns201() throws Exception {
        when(organizationService.create(any(OrganizationRequest.class))).thenReturn(new OrganizationResponse(1L, "PXL"));

        mockMvc.perform(post("/organizations").contentType(MediaType.APPLICATION_JSON).content("{\"name\": \"PXL\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("PXL"));
    }

    @Test
    void create_blankName_returns400AndNeverCallsService() throws Exception {
        mockMvc.perform(post("/organizations").contentType(MediaType.APPLICATION_JSON).content("{\"name\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists());

        verify(organizationService, never()).create(any());
    }

    @Test
    void findAll_returns200() throws Exception {
        when(organizationService.findAll()).thenReturn(List.of(new OrganizationResponse(1L, "PXL")));

        mockMvc.perform(get("/organizations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("PXL"));
    }

    @Test
    void findById_unknownId_returns404() throws Exception {
        when(organizationService.findById(99L)).thenThrow(new OrganizationNotFoundException(99L));

        mockMvc.perform(get("/organizations/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void delete_existingId_returns204() throws Exception {
        mockMvc.perform(delete("/organizations/5"))
                .andExpect(status().isNoContent());

        verify(organizationService).delete(5L);
    }

    @Test
    void findByIdWithDepartments_returns200() throws Exception {
        when(organizationService.findByIdWithDepartments(1L)).thenReturn(
                new OrganizationWithDepartmentsResponse(1L, "PXL", List.of(new DepartmentDto(1L, "IT"))));

        mockMvc.perform(get("/organizations/1/with-departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("PXL"))
                .andExpect(jsonPath("$.departments[0].name").value("IT"));
    }

    @Test
    void findByIdWithEmployees_returns200() throws Exception {
        when(organizationService.findByIdWithEmployees(1L)).thenReturn(
                new OrganizationWithEmployeesResponse(1L, "PXL",
                        List.of(new EmployeeDto(7L, "Jan", "Peeters", "jan@pxl.be"))));

        mockMvc.perform(get("/organizations/1/with-employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employees[0].firstName").value("Jan"));
    }

    @Test
    void findByIdWithDepartmentsAndEmployees_returns200() throws Exception {
        EmployeeDto employee = new EmployeeDto(7L, "Jan", "Peeters", "jan@pxl.be");
        when(organizationService.findByIdWithDepartmentsAndEmployees(1L)).thenReturn(
                new OrganizationWithDepartmentsAndEmployeesResponse(1L, "PXL",
                        List.of(new DepartmentWithEmployeesDto(1L, "IT", List.of(employee)))));

        mockMvc.perform(get("/organizations/1/with-departments-and-employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departments[0].name").value("IT"))
                .andExpect(jsonPath("$.departments[0].employees[0].lastName").value("Peeters"));
    }
}
