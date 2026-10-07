package be.pxl.notification.service;

import be.pxl.notification.dto.NotificationRequest;
import be.pxl.notification.dto.NotificationResponse;
import be.pxl.notification.Model.Notification;
import be.pxl.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public NotificationResponse send(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setRecipient(request.recipient());
        notification.setMessage(request.message());

        Notification saved = notificationRepository.save(notification);

        return new NotificationResponse(saved.getId(), saved.getRecipient(), saved.getMessage());
    }
}
