package be.pxl.delivery.controller;

import be.pxl.delivery.dto.response.RunResponse;
import be.pxl.delivery.dto.request.SlotRequest;
import be.pxl.delivery.dto.response.SlotResponse;
import be.pxl.delivery.service.DeliveryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/slots")
    public List<SlotResponse> findSlots() {
        return deliveryService.findSlots();
    }

    @PostMapping("/slots")
    @ResponseStatus(HttpStatus.CREATED)
    public SlotResponse createSlot(@RequestHeader(value = "X-User-Role", required = false) String role,
                                   @Valid @RequestBody SlotRequest request) {
        return deliveryService.createSlot(role, request);
    }

    @GetMapping("/runs/mine")
    public List<RunResponse> findMyRuns(@RequestHeader(value = "X-User-Role", required = false) String role,
                                        @RequestHeader(value = "X-User-Id", required = false) String userId) {
        return deliveryService.findMyRuns(role, userId);
    }

    @PostMapping("/runs/{id}/start")
    public RunResponse startRun(@RequestHeader(value = "X-User-Role", required = false) String role,
                                @RequestHeader(value = "X-User-Id", required = false) String userId,
                                @PathVariable Long id) {
        return deliveryService.startRun(role, userId, id);
    }

    @PostMapping("/runs/{id}/stops/{orderId}/delivered")
    public RunResponse markDelivered(@RequestHeader(value = "X-User-Role", required = false) String role,
                                     @RequestHeader(value = "X-User-Id", required = false) String userId,
                                     @PathVariable Long id,
                                     @PathVariable Long orderId) {
        return deliveryService.markDelivered(role, userId, id, orderId);
    }
}