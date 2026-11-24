package be.pxl.delivery.service;



import be.pxl.delivery.dto.response.RunResponse;
import be.pxl.delivery.dto.response.RunStopResponse;
import be.pxl.delivery.dto.request.SlotRequest;
import be.pxl.delivery.dto.response.SlotResponse;
import be.pxl.delivery.exception.ForbiddenException;
import be.pxl.delivery.exception.RunNotFoundException;
import be.pxl.delivery.exception.StopNotFoundException;
import be.pxl.delivery.model.DeliveryRun;
import be.pxl.delivery.model.DeliverySlot;
import be.pxl.delivery.model.RunStop;
import be.pxl.delivery.repository.DeliveryRunRepository;
import be.pxl.delivery.repository.DeliverySlotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryService {

    private static final Logger log = LoggerFactory.getLogger(DeliveryService.class);

    public static final String DEFAULT_DRIVER = "chauffeur";

    private final DeliverySlotRepository slotRepository;
    private final DeliveryRunRepository runRepository;

    public DeliveryService(DeliverySlotRepository slotRepository,
                           DeliveryRunRepository runRepository) {
        this.slotRepository = slotRepository;
        this.runRepository = runRepository;
    }

    public List<SlotResponse> findSlots() {
        List<SlotResponse> result = new ArrayList<>();

        for (DeliverySlot slot : slotRepository.findAllByOrderByDateAscStartTimeAsc()) {
            result.add(toResponse(slot));
        }

        return result;
    }

    public SlotResponse createSlot(String role, SlotRequest request) {
        if (!"MEDEWERKER".equals(role)) {
            throw new ForbiddenException(
                    "Enkel de rol MEDEWERKER mag leverslots aanmaken."
            );
        }

        DeliverySlot slot = new DeliverySlot(
                request.date(),
                request.startTime(),
                request.endTime(),
                request.capacity()
        );

        return toResponse(slotRepository.save(slot));
    }

    public List<RunResponse> findMyRuns(String role, String driverId) {
        checkChauffeur(role);

        List<RunResponse> result = new ArrayList<>();

        for (DeliveryRun run : runRepository.findByDriverId(driverId)) {
            result.add(toResponse(run));
        }

        return result;
    }

    public RunResponse startRun(String role, String driverId, Long runId) {
        checkChauffeur(role);

        DeliveryRun run = getOwnRun(driverId, runId);

        run.start();

        DeliveryRun saved = runRepository.save(run);

        log.info("Rit {} gestart", runId);

        return toResponse(saved);
    }

    public RunResponse markDelivered(String role,
                                     String driverId,
                                     Long runId,
                                     Long orderId) {

        checkChauffeur(role);

        DeliveryRun run = getOwnRun(driverId, runId);

        if (run.findStop(orderId) == null) {
            throw new StopNotFoundException(runId, orderId);
        }

        run.markDelivered(orderId);

        DeliveryRun saved = runRepository.save(run);

        log.info("Bestelling {} geleverd in rit {}", orderId, runId);

        return toResponse(saved);
    }

    private void checkChauffeur(String role) {
        if (!"CHAUFFEUR".equals(role)) {
            throw new ForbiddenException(
                    "Enkel de rol CHAUFFEUR mag ritten beheren."
            );
        }
    }

    private DeliveryRun getOwnRun(String driverId, Long runId) {

        DeliveryRun run = runRepository.findById(runId)
                .orElseThrow(() -> new RunNotFoundException(runId));

        if (!run.getDriverId().equals(driverId)) {
            throw new ForbiddenException(
                    "Deze rit hoort niet bij jou."
            );
        }

        return run;
    }

    private SlotResponse toResponse(DeliverySlot slot) {

        return new SlotResponse(
                slot.getId(),
                slot.getDate(),
                slot.getStartTime(),
                slot.getEndTime(),
                slot.getCapacity(),
                slot.getBooked(),
                slot.freePlaces()
        );
    }

    private RunResponse toResponse(DeliveryRun run) {

        List<RunStopResponse> stops = new ArrayList<>();

        for (RunStop stop : run.getStops()) {
            stops.add(
                    new RunStopResponse(
                            stop.getOrderId(),
                            stop.getAddress(),
                            stop.isDelivered()
                    )
            );
        }

        return new RunResponse(
                run.getId(),
                run.getSlotId(),
                run.getDriverId(),
                run.getStatus(),
                stops
        );
    }
}