package be.pxl.delivery.repository;

import be.pxl.delivery.model.DeliveryRun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryRunRepository extends JpaRepository<DeliveryRun, Long> {

    Optional<DeliveryRun> findBySlotId(Long slotId);

    List<DeliveryRun> findByDriverId(String driverId);
}