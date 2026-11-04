package be.pxl.notification.service;

import be.pxl.notification.dto.EmployeeCreatedEvent;
import be.pxl.notification.dto.NotificationRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeEventListenerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private EmployeeEventListener listener;

    @Test
    void onEmployeeCreated_sendsNotificationToEmployeeEmail() {
        listener.onEmployeeCreated(new EmployeeCreatedEvent(7L, "jan@pxl.be"));

        ArgumentCaptor<NotificationRequest> captor = ArgumentCaptor.forClass(NotificationRequest.class);
        verify(notificationService).send(captor.capture());
        assertEquals("jan@pxl.be", captor.getValue().recipient());
        assertEquals("Werknemer 7 is aangemaakt.", captor.getValue().message());
    }
}
