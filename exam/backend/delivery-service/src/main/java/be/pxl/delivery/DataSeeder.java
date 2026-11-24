package be.pxl.delivery;

import be.pxl.delivery.model.DeliverySlot;
import be.pxl.delivery.repository.DeliverySlotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@Profile("!test")
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final DeliverySlotRepository slotRepository;

    public DataSeeder(DeliverySlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    @Override
    public void run(String... args) {
        if (slotRepository.count() > 0) {
            return;
        }
        for (int day = 1; day <= 4; day++) {
            LocalDate date = LocalDate.now().plusDays(day);
            slotRepository.save(new DeliverySlot(date, LocalTime.of(12, 0), LocalTime.of(14, 0), 5));
            slotRepository.save(new DeliverySlot(date, LocalTime.of(17, 0), LocalTime.of(19, 0), 5));
            slotRepository.save(new DeliverySlot(date, LocalTime.of(19, 0), LocalTime.of(21, 0), 2));
        }
        log.info("Leverslots voor de komende 4 dagen aangemaakt");
    }
}