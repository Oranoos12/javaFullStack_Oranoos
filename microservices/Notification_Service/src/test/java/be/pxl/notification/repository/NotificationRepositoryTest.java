package be.pxl.notification.repository;

import be.pxl.notification.Model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class NotificationRepositoryTest {

    @Container
    @ServiceConnection
    static MySQLContainer mysql = new MySQLContainer("mysql:8.4");

    @Autowired
    private NotificationRepository notificationRepository;

    @BeforeEach
    void cleanDatabase() {
        notificationRepository.deleteAll();
    }

    @Test
    void savesAndFindsNotification() {
        Notification notification = new Notification();
        notification.setRecipient("jan@pxl.be");
        notification.setMessage("Welkom bij PXL");

        Notification saved = notificationRepository.save(notification);

        Notification found = notificationRepository.findById(saved.getId()).orElseThrow();
        assertEquals("jan@pxl.be", found.getRecipient());
        assertEquals("Welkom bij PXL", found.getMessage());
    }

    @Test
    void findAll_returnsAllSavedNotifications() {
        Notification first = new Notification();
        first.setRecipient("jan@pxl.be");
        first.setMessage("Eerste");
        Notification second = new Notification();
        second.setRecipient("piet@pxl.be");
        second.setMessage("Tweede");
        notificationRepository.save(first);
        notificationRepository.save(second);

        assertEquals(2, notificationRepository.findAll().size());
    }
}