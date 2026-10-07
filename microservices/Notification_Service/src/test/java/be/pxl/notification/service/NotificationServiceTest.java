package be.pxl.notification.service;

import be.pxl.notification.dto.NotificationRequest;
import be.pxl.notification.dto.NotificationResponse;
import be.pxl.notification.Model.Notification;
import be.pxl.notification.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void send_savesNotificationWithRecipientAndMessage() {
        Notification saved = new Notification();
        saved.setId(10L);
        saved.setRecipient("jan@pxl.be");
        saved.setMessage("Welkom bij PXL");
        when(notificationRepository.save(any(Notification.class))).thenReturn(saved);

        NotificationResponse response = notificationService.send(new NotificationRequest("jan@pxl.be", "Welkom bij PXL"));

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(captor.capture());
        assertEquals("jan@pxl.be", captor.getValue().getRecipient());
        assertEquals("Welkom bij PXL", captor.getValue().getMessage());
        assertEquals(10L, response.id());
    }
}
