package be.pxl.delivery.dto.response;

import be.pxl.delivery.model.RunStatus;
import java.util.List;

public record RunResponse(
        Long id,
        Long slotId,
        String driverId,
        RunStatus status,
        List<RunStopResponse> stops
) {
}
