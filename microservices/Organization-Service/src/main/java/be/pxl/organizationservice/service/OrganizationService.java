package be.pxl.organizationservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import be.pxl.organizationservice.dto.OrganizationRequest;
import be.pxl.organizationservice.dto.OrganizationResponse;
import be.pxl.organizationservice.exception.OrganizationNotFoundException;
import be.pxl.organizationservice.model.Organization;
import be.pxl.organizationservice.repository.OrganizationRepository;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public OrganizationResponse create(OrganizationRequest request) {
        Organization organization = new Organization();
        organization.setName(request.name());
        return toResponse(organizationRepository.save(organization));
    }

    public List<OrganizationResponse> findAll() {
        return organizationRepository.findAll().stream().map(this::toResponse).toList();
    }

    public OrganizationResponse findById(Long id) {
        return toResponse(getOrganization(id));
    }

    public OrganizationResponse update(Long id, OrganizationRequest request) {
        Organization organization = getOrganization(id);
        organization.setName(request.name());
        return toResponse(organizationRepository.save(organization));
    }

    public void delete(Long id) {
        if (!organizationRepository.existsById(id)) {
            throw new OrganizationNotFoundException(id);
        }
        organizationRepository.deleteById(id);
    }

    // --- hulpmethodes ---

    private Organization getOrganization(Long id) {
        return organizationRepository.findById(id).orElseThrow(() -> new OrganizationNotFoundException(id));
    }

    private OrganizationResponse toResponse(Organization organization) {
        return new OrganizationResponse(organization.getId(), organization.getName());
    }
}
