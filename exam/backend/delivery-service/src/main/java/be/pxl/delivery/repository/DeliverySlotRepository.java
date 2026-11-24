package be.pxl.delivery.repository;

import be.pxl.delivery.model.DeliverySlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliverySlotRepository extends JpaRepository<DeliverySlot, Long> {

    List<DeliverySlot> findAllByOrderByDateAscStartTimeAsc();
}