package be.pxl.organizationservice.exception;

public class OrganizationNotFoundException extends RuntimeException {

    public OrganizationNotFoundException(Long id) {
        super("Organization met id " + id + " bestaat niet.");
    }
}
