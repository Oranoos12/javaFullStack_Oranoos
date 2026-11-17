package be.pxl.inventory.service;

import be.pxl.inventory.dto.request.AvailabilityRequest;
import be.pxl.inventory.dto.response.AvailabilityResponse;
import be.pxl.inventory.dto.event.IngredientCreatedEvent;
import be.pxl.inventory.dto.event.OrderCancelledEvent;
import be.pxl.inventory.dto.event.OrderConfirmedEvent;
import be.pxl.inventory.dto.request.OrderItem;
import be.pxl.inventory.dto.request.ReplenishRequest;
import be.pxl.inventory.dto.response.StockItemResponse;
import be.pxl.inventory.exception.ForbiddenException;
import be.pxl.inventory.exception.StockItemNotFoundException;
import be.pxl.inventory.model.Reservation;
import be.pxl.inventory.model.StockItem;
import be.pxl.inventory.repository.ReservationRepository;
import be.pxl.inventory.repository.StockItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);
    private static final double DEFAULT_MINIMUM = 500;

    private final StockItemRepository stockItemRepository;
    private final ReservationRepository reservationRepository;

    public InventoryService(StockItemRepository stockItemRepository, ReservationRepository reservationRepository) {
        this.stockItemRepository = stockItemRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<StockItemResponse> findAll(String role) {
        checkMedewerker(role);
        List<StockItemResponse> result = new ArrayList<>();
        for (StockItem item : stockItemRepository.findAll()) {
            result.add(toResponse(item));
        }
        return result;
    }

    public List<StockItemResponse> findLowStock(String role) {
        checkMedewerker(role);
        List<StockItemResponse> result = new ArrayList<>();
        for (StockItem item : stockItemRepository.findAll()) {
            if (item.isLow()) {
                result.add(toResponse(item));
            }
        }
        return result;
    }

    public StockItemResponse replenish(String role, Long ingredientId, ReplenishRequest request) {
        checkMedewerker(role);
        StockItem item = stockItemRepository.findByIngredientId(ingredientId)
                .orElseThrow(() -> new StockItemNotFoundException(ingredientId));
        item.replenish(request.quantity());
        StockItem saved = stockItemRepository.save(item);
        log.info("Voorraad van {} aangevuld tot {} {}", saved.getIngredientName(), saved.getQuantityAvailable(), saved.getUnit());
        return toResponse(saved);
    }

    public AvailabilityResponse checkAvailability(AvailabilityRequest request) {
        for (OrderItem orderItem : request.items()) {
            Optional<StockItem> stock = stockItemRepository.findByIngredientId(orderItem.ingredientId());
            if (stock.isEmpty()) {
                return new AvailabilityResponse(false, "Ingrediënt " + orderItem.ingredientId() + " is niet in voorraad.");
            }
            StockItem item = stock.get();
            if (!item.canReserve(orderItem.quantity())) {
                return new AvailabilityResponse(false, "Onvoldoende " + item.getIngredientName()
                        + ": nodig " + orderItem.quantity() + " " + item.getUnit()
                        + ", beschikbaar " + item.getQuantityAvailable() + " " + item.getUnit() + ".");
            }
        }
        return new AvailabilityResponse(true, "Voorraad is voldoende.");
    }

    public void createStockItem(IngredientCreatedEvent event) {
        if (stockItemRepository.findByIngredientId(event.ingredientId()).isPresent()) {
            return;
        }
        StockItem item = new StockItem();
        item.setIngredientId(event.ingredientId());
        item.setIngredientName(event.name());
        item.setUnit(event.unit());
        item.setQuantityAvailable(0);
        item.setMinimumLevel(DEFAULT_MINIMUM);
        stockItemRepository.save(item);
        log.info("Voorraaditem aangemaakt voor {}", event.name());
    }

    @Transactional
    public void reserveForOrder(OrderConfirmedEvent event) {
        for (OrderItem orderItem : event.items()) {
            Optional<StockItem> stock = stockItemRepository.findByIngredientId(orderItem.ingredientId());
            if (stock.isEmpty() || !stock.get().canReserve(orderItem.quantity())) {
                log.warn("Reservatie mislukt voor bestelling {}: te weinig van ingrediënt {}", event.orderId(), orderItem.ingredientId());
                continue;
            }
            StockItem item = stock.get();
            item.reserve(orderItem.quantity());
            stockItemRepository.save(item);
            reservationRepository.save(new Reservation(event.orderId(), orderItem.ingredientId(), orderItem.quantity()));
        }
        log.info("Voorraad gereserveerd voor bestelling {}", event.orderId());
    }

    @Transactional
    public void releaseForOrder(OrderCancelledEvent event) {
        List<Reservation> reservations = reservationRepository.findByOrderId(event.orderId());
        for (Reservation reservation : reservations) {
            Optional<StockItem> stock = stockItemRepository.findByIngredientId(reservation.getIngredientId());
            if (stock.isPresent()) {
                StockItem item = stock.get();
                item.release(reservation.getQuantity());
                stockItemRepository.save(item);
            }
            reservationRepository.delete(reservation);
        }
        log.info("Reservaties vrijgegeven voor bestelling {}", event.orderId());
    }

    private void checkMedewerker(String role) {
        if (!"MEDEWERKER".equals(role)) {
            throw new ForbiddenException("MEDEWERKER");
        }
    }

    private StockItemResponse toResponse(StockItem item) {
        return new StockItemResponse(
                item.getId(),
                item.getIngredientId(),
                item.getIngredientName(),
                item.getUnit(),
                item.getQuantityAvailable(),
                item.getMinimumLevel(),
                item.isLow());
    }
}
