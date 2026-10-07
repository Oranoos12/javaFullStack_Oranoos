package be.pxl.notification.controller;

import be.pxl.notification.dto.NotificationRequest;
import be.pxl.notification.dto.NotificationResponse;
import be.pxl.notification.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificationService notificationService;

    @Test
    void send_validRequest_returns201() throws Exception {
        when(notificationService.send(any(NotificationRequest.class)))
                .thenReturn(new NotificationResponse(1L, "jan@pxl.be", "Welkom"));

        mockMvc.perform(post("/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"recipient\": \"jan@pxl.be\", \"message\": \"Welkom\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.recipient").value("jan@pxl.be"));
    }

    @Test
    void send_blankFields_returns400AndNeverCallsService() throws Exception {
        mockMvc.perform(post("/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"recipient\": \"\", \"message\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.recipient").exists())
                .andExpect(jsonPath("$.message").exists());

        verify(notificationService, never()).send(any());
    }
}