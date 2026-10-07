package be.pxl.organizationservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import be.pxl.organizationservice.dto.OrganizationRequest;
import be.pxl.organizationservice.dto.OrganizationResponse;
import be.pxl.organizationservice.exception.OrganizationNotFoundException;
import be.pxl.organizationservice.model.Organization;
import be.pxl.organizationservice.repository.OrganizationRepository;

@ExtendWith(MockitoExtension.class)
class OrganizationServiceTest {

    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private OrganizationService organizationService;

    private Organization createOrganization(Long id) {
        Organization organization = new Organization();
        organization.setId(id);
        organization.setName("PXL");
        return organization;
    }

    @Test
    void create_savesOrganizationWithName() {
        when(organizationRepository.save(any(Organization.class))).thenReturn(createOrganization(10L));

        OrganizationResponse response = organizationService.create(new OrganizationRequest("PXL"));

        ArgumentCaptor<Organization> captor = ArgumentCaptor.forClass(Organization.class);
        verify(organizationRepository).save(captor.capture());
        assertEquals("PXL", captor.getValue().getName());
        assertEquals(10L, response.id());
    }

    @Test
    void findById_existingOrganization_returnsResponse() {
        when(organizationRepository.findById(5L)).thenReturn(Optional.of(createOrganization(5L)));

        OrganizationResponse response = organizationService.findById(5L);

        assertEquals(5L, response.id());
        assertEquals("PXL", response.name());
    }

    @Test
    void findById_unknownOrganization_throwsNotFound() {
        when(organizationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(OrganizationNotFoundException.class, () -> organizationService.findById(99L));
    }

    @Test
    void findAll_returnsAllOrganizations() {
        when(organizationRepository.findAll()).thenReturn(List.of(createOrganization(1L), createOrganization(2L)));

        List<OrganizationResponse> result = organizationService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void update_existingOrganization_savesNewName() {
        Organization existing = createOrganization(5L);
        when(organizationRepository.findById(5L)).thenReturn(Optional.of(existing));
        when(organizationRepository.save(any(Organization.class))).thenReturn(existing);

        organizationService.update(5L, new OrganizationRequest("Nieuwe naam"));

        ArgumentCaptor<Organization> captor = ArgumentCaptor.forClass(Organization.class);
        verify(organizationRepository).save(captor.capture());
        assertEquals("Nieuwe naam", captor.getValue().getName());
    }

    @Test
    void update_unknownOrganization_neverSaves() {
        when(organizationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(OrganizationNotFoundException.class,
                () -> organizationService.update(99L, new OrganizationRequest("X")));

        verify(organizationRepository, never()).save(any());
    }

    @Test
    void delete_existingOrganization_deletesIt() {
        when(organizationRepository.existsById(5L)).thenReturn(true);

        organizationService.delete(5L);

        verify(organizationRepository).deleteById(5L);
    }

    @Test
    void delete_unknownOrganization_neverDeletes() {
        when(organizationRepository.existsById(99L)).thenReturn(false);

        assertThrows(OrganizationNotFoundException.class, () -> organizationService.delete(99L));

        verify(organizationRepository, never()).deleteById(any());
    }
}
