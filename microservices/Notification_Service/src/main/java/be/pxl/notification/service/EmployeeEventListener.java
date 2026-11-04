package be.pxl.notification.service;

import be.pxl.notification.dto.EmployeeCreatedEvent;
import be.pxl.notification.dto.NotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EmployeeEventListener {

    private static final Logger log = LoggerFactory.getLogger(EmployeeEventListener.class);

    private final NotificationService notificationService;

    public EmployeeEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "employee.created")
    public void onEmployeeCreated(EmployeeCreatedEvent event) {
        log.info("Melding versturen naar {}", event.email());
        notificationService.send(new NotificationRequest(event.email(), "Werknemer " + event.id() + " is aangemaakt."));
    }
}
