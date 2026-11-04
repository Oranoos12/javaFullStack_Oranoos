package be.pxl.employeeservice.Client;

import be.pxl.employeeservice.Dto.NotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotificationFallback implements NotificationClient {

    private static final Logger log = LoggerFactory.getLogger(NotificationFallback.class);

    @Override
    public void sendNotification(NotificationRequest request) {
        log.warn("notification-service onbereikbaar, melding overgeslagen");
    }
}
