package be.pxl.notification.service;

import be.pxl.notification.dto.NotificationRequest;
import be.pxl.notification.dto.NotificationResponse;
import be.pxl.notification.Model.Notification;
import be.pxl.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public NotificationResponse send(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setRecipient(request.recipient());
        notification.setMessage(request.message());

        Notification saved = notificationRepository.save(notification);

        log.info("Notification voor {}: {}", saved.getRecipient(), saved.getMessage());

        return new NotificationResponse(saved.getId(), saved.getRecipient(), saved.getMessage());
    }
}
