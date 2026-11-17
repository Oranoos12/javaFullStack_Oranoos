package be.pxl.order.exception;

public class SlotFullException extends RuntimeException {

    public SlotFullException(Long slotId) {
        super("Leverslot " + slotId + " is volzet of bestaat niet.");
    }
}
