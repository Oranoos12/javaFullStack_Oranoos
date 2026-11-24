package be.pxl.inventory.controller;

import be.pxl.inventory.dto.request.AvailabilityRequest;
import be.pxl.inventory.dto.response.AvailabilityResponse;
import be.pxl.inventory.dto.request.ReplenishRequest;
import be.pxl.inventory.dto.response.StockItemResponse;
import be.pxl.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<StockItemResponse> findAll(@RequestHeader(value = "X-User-Role", required = false) String role) {
        return inventoryService.findAll(role);
    }

    @GetMapping("/low-stock")
    public List<StockItemResponse> findLowStock(@RequestHeader(value = "X-User-Role", required = false) String role) {
        return inventoryService.findLowStock(role);
    }

    @PostMapping("/{ingredientId}/replenish")
    public StockItemResponse replenish(@RequestHeader(value = "X-User-Role", required = false) String role,
                                       @PathVariable Long ingredientId,
                                       @Valid @RequestBody ReplenishRequest request) {
        return inventoryService.replenish(role, ingredientId, request);
    }

    @PostMapping("/availability")
    public AvailabilityResponse checkAvailability(@RequestBody AvailabilityRequest request) {
        return inventoryService.checkAvailability(request);
    }
}
